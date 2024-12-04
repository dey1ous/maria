
        // Example notifications data (this would typically come from an API)
        const notifications = [
            "Your loan application has been approved.",
            "New updates are available in your account.",
            "Your payment is due soon."
        ];

        const notificationList = document.getElementById('notificationList');

        // Function to render notifications
        function renderNotifications() {
            notificationList.innerHTML = ''; // Clear existing notifications
            notifications.forEach((notification, index) => {
                const li = document.createElement('li');
                li.textContent = notification;

                // Create a close button
                const closeBtn = document.createElement('span');
                closeBtn.textContent = '✖';
                closeBtn.className = 'close-btn';
                closeBtn.onclick = () => {
                    notifications.splice(index, 1); // Remove notification from array
                    renderNotifications(); // Re-render notifications
                };

                li.appendChild(closeBtn);
                notificationList.appendChild(li);
            });
        }

        // Initial render
        renderNotifications();
