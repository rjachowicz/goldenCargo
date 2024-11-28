const modalBody = document.getElementById('modalBody');
const confirmDeleteButton = document.getElementById('confirmDeleteButton');
const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));

function showDeleteModal(button) {
    const entityId = button.getAttribute('data-entity-id');
    const entityType = button.getAttribute('data-entity-type') || 'item';
    const deleteUrl = button.getAttribute('data-delete-url').replace("{id}", entityId);

    updateModalMessage(`Are you sure you want to delete this ${entityType}?`);
    confirmDeleteButton.onclick = () => handleDelete(deleteUrl);
    confirmDeleteModal.show();
}

function updateModalMessage(message) {
    modalBody.textContent = message;
}

function handleDelete(url) {
    fetch(url, {method: 'DELETE'})
        .then(response => {
            if (response.ok) {
                updateModalMessage('Deleted successfully');
            } else {
                updateModalMessage('Failed to delete');
            }
            closeModal();
        })
        .catch(error => {
            updateModalMessage(`An error occurred: ${error.message}`);
            closeModal();
        });
}

function closeModal() {
    confirmDeleteButton.style.display = 'none';
    setTimeout(() => {
        confirmDeleteModal.hide();
        window.location.reload();
    }, 1500);
}
