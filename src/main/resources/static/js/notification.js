async function fetchUserIdFromSession() {
    try {
        // Fetch the userId from the backend session endpoint
        const response = await fetch('/api/session/userId', {
            method: 'GET',
            credentials: 'same-origin'  // Ensure cookies (JSESSIONID) are sent with the request
        });

        if (!response.ok) {
            throw new Error('Failed to fetch userId from session');
        }

        const userId = await response.json();

        if (userId !== null) {
            // Store the userId in sessionStorage or handle it accordingly
            sessionStorage.clear();
            sessionStorage.setItem('userId', userId);
        } else {
            console.error('User is not logged in');
        }
    } catch (error) {
        console.error(error.message);
    }
}

async function loadNotifications() {
    try {
        // First, ensure that userId is fetched from session if not already stored
        let userId = sessionStorage.getItem('userId');
        
        if (!userId) {
            // If userId is not available in sessionStorage, fetch it from the backend
            await fetchUserIdFromSession();
            userId = sessionStorage.getItem('userId');
        }

        if (!userId) {
            throw new Error("User is not logged in or userId is not available in session.");
        }

        // Fetch the notifications with userId as a query parameter
        const response = await fetch(`/api/notifications?userId=${userId}`, {
            method: 'GET',
            credentials: 'same-origin',  // Ensures cookies (JSESSIONID) are sent along with the request
        });

        if (!response.ok) {
            throw new Error(`Failed to fetch notifications: ${response.statusText}`);
        }

        const notifications = await response.json();
        const notificationTable = document.getElementById('notificationTable');

        notificationTable.innerHTML = ''; // Clear previous notifications

        notifications.forEach(notification => {
            const row = document.createElement('tr');
            const messageCell = document.createElement('td');
            messageCell.textContent = notification.message;

            const timestampCell = document.createElement('td');
            timestampCell.textContent = new Date(notification.timestamp).toLocaleString();

            row.appendChild(messageCell);
            row.appendChild(timestampCell);
            notificationTable.appendChild(row);
        });
    } catch (error) {
        console.error(error.message);
    }
}

// Call loadNotifications to load the notifications
loadNotifications();
