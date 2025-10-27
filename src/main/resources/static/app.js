// very simple JS - barebones, jst works
document.addEventListener('DOMContentLoaded', function () {
  var createForm = document.querySelector('form[action="/ab"]');
  if (createForm) {
    createForm.addEventListener('submit', onCreateAb);
  }

  var addForm = document.getElementById('addBuddyForm');
  if (addForm) {
    addForm.addEventListener('submit', onAddBuddy);
  }

  // make remove buttons use API too
  var removeForms = document.querySelectorAll('form[action*="/buddy/"][method="post"]');
  for (var i = 0; i < removeForms.length; i++) {
    removeForms[i].addEventListener('submit', onRemoveBuddy);
  }
});

function onCreateAb(e) {
  e.preventDefault();
  fetch('/api/ab', { method: 'POST' })
    .then(function (r) { return r.ok ? r.json() : Promise.reject(); })
    .then(function (ab) { window.location.href = '/ab/find/' + ab.id; })
    .catch(function () { e.target.submit(); });
}

function onAddBuddy(e) {
  e.preventDefault();
  var form = e.target;
  var fd = new FormData(form);
  var name = fd.get('name');
  var phone = fd.get('phone');
  var parts = window.location.pathname.split('/');
  var id = parts[parts.length - 1];

  var body = new URLSearchParams();
  body.set('name', name);
  body.set('phone', phone);

  fetch('/api/ab/' + id + '/buddies', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: body.toString()
  })
    .then(function (r) { return r.ok ? r.json() : Promise.reject(); })
    .then(function (ab) {
      renderBuddies(ab);
      form.reset();
    })
    .catch(function () { form.submit(); });
}

function onRemoveBuddy(e) {
  e.preventDefault();
  var action = e.target.action; // .../ab/{id}/buddy/{buddyId}/delete
  var parts = action.split('/');
  var id = parts[parts.indexOf('ab') + 1];
  var buddyId = parts[parts.indexOf('buddy') + 1];

  fetch('/api/ab/' + id + '/buddies/' + buddyId, { method: 'DELETE' })
    .then(function (r) { return r.ok ? r.json() : Promise.reject(); })
    .then(function (ab) { renderBuddies(ab); })
    .catch(function () { e.target.submit(); });
}

function renderBuddies(ab) {
  var table = document.getElementById('buddiesTable');
  if (!table) return;

  // header
  var html = '<tr><th>Name</th><th>Phone</th><th>Actions</th></tr>';

  var list = ab.buddies || [];
  for (var i = 0; i < list.length; i++) {
    var b = list[i];
    html += '<tr>'
      + '<td>' + escapeHtml(b.name) + '</td>'
      + '<td>' + escapeHtml(b.phoneNumber) + '</td>'
      + '<td>'
      + '<form action="/ab/' + ab.id + '/buddy/' + b.id + '/delete" method="post">'
      + '<input type="submit" value="Remove">'
      + '</form>'
      + '</td>'
      + '</tr>';
  }
  table.innerHTML = html;

  // re-bind remove handlers for new forms
  var removeForms = table.querySelectorAll('form[action*="/buddy/"][method="post"]');
  for (var j = 0; j < removeForms.length; j++) {
    removeForms[j].addEventListener('submit', onRemoveBuddy);
  }
}

function escapeHtml(s) {
  return String(s).replace(/[&<>"']/g, function (c) {
    return { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c];
  });
}
