// Runs on pages that include it. Enhances forms instead of replacing them.
document.addEventListener('DOMContentLoaded', () => {
  const addForm = document.getElementById('addBuddyForm');
  if (addForm) hookAddBuddy(addForm);

  const abCreateForm = document.querySelector('form[action="/ab"]');
  if (abCreateForm) hookCreateAb(abCreateForm);

  // Optional: turn remove buttons into AJAX if present
  enhanceRemoveButtons();
});

function hookCreateAb(form) {
  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const res = await fetch('/api/ab', { method: 'POST' });
    if (!res.ok) return form.submit(); // fallback
    const ab = await res.json();
    window.location.assign(`/ab/find/${ab.id}`);
  });
}

function hookAddBuddy(form) {
  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const fd = new FormData(form);
    const id = window.location.pathname.split('/').pop();
    const params = new URLSearchParams({ name: fd.get('name'), phone: fd.get('phone') });
    const res = await fetch(`/api/ab/${id}/buddies`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params.toString()
    });
    if (!res.ok) return form.submit(); // fallback
    const ab = await res.json();
    renderBuddies(ab);
    form.reset();
  });
}

function enhanceRemoveButtons() {
  document.querySelectorAll('form[action*="/buddy/"][method="post"]').forEach(form => {
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const parts = form.action.split('/'); // .../ab/{id}/buddy/{buddyId}/delete
      const id = parts[parts.indexOf('ab') + 1];
      const buddyId = parts[parts.indexOf('buddy') + 1];
      const res = await fetch(`/api/ab/${id}/buddies/${buddyId}`, { method: 'DELETE' });
      if (!res.ok) return form.submit(); // fallback
      const ab = await res.json();
      renderBuddies(ab);
    });
  });
}

function renderBuddies(ab) {
  const table = document.getElementById('buddiesTable');
  if (!table) return;
  // wipe rows except header
  table.querySelectorAll('tr:not(:first-child)').forEach(tr => tr.remove());
  (ab.buddies || []).forEach(b => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${escapeHtml(b.name)}</td>
      <td>${escapeHtml(b.phoneNumber)}</td>
      <td>
        <form action="/ab/${ab.id}/buddy/${b.id}/delete" method="post">
          <button type="submit">Remove</button>
        </form>
      </td>`;
    table.appendChild(tr);
  });
}

function escapeHtml(s) {
  return String(s).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
}