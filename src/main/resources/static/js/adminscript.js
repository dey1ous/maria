// Function to open the sidebar
function openNav() {
    document.getElementById("sidebar").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

// Function to close the sidebar
function closeNav() {
    document.getElementById("sidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
}

// Function to load content dynamically
function loadContent(file) {
    fetch(file)
        .then(response => response.text())
        .then(html => {
            document.getElementById("content").innerHTML = html;
        })
        .catch(error => console.error('Error loading content:', error));
}

// Show Admin Loan View
function showAdminLoans() {
    document.getElementById("adminLoanSection").style.display = 'block';  // Show the admin loan section
    fetchAllLoans(); // Fetch loans for the admin
}

async function fetchAllLoans() {
    try {
        const response = await fetch('/admin/loans');
        if (!response.ok) throw new Error('Network response was not ok');

        const loans = await response.json();
        const tableBody = document.getElementById("adminLoanTable").querySelector("tbody");
        tableBody.innerHTML = ""; // Clear existing rows

        if (loans.length > 0) {
            loans.forEach(loan => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${loan.id}</td>  <!-- Loan ID -->
                    <td>${loan.name}</td>
                    <td>${loan.amount.toFixed(2)}</td>  <!-- Amount -->
                    <td>${loan.status}</td>  <!-- Status -->
                    <td>${new Date(loan.applicationDate).toLocaleString()}</td>  <!-- Application Date -->
                    <td>
                        <button onclick="approveLoan(${loan.id})">Approve</button>
                        <button onclick="rejectLoan(${loan.id})">Reject</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            const row = document.createElement("tr");
            row.innerHTML = "<td colspan='5'>No loan applications found</td>"; // Adjusted colspan
            tableBody.appendChild(row);
        }
    } catch (error) {
        console.error("Error fetching loan applications:", error);
        alert("Failed to fetch loan applications.");
    }
}

async function approveLoan(loanId) {
    try {
        // Make PUT request to approve the loan
        const response = await fetch(`/api/loans/${loanId}/approve`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to approve the loan');
        }

        const loan = await response.json(); // Assuming the backend returns the updated loan
        alert(`Loan approved successfully: ${loan.id}`);
        // Optionally, refresh the loan list to reflect the updated status
        fetchAllLoans();
    } catch (error) {
        console.error('Error approving loan:', error);
        alert('Failed to approve loan. Please try again.');
    }
}

// Reject a loan
async function rejectLoan(loanId) {
    try {
        // Make PUT request to reject the loan
        const response = await fetch(`/api/loans/${loanId}/reject`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Failed to reject the loan');
        }

        const loan = await response.json(); // Assuming the backend returns the updated loan
        alert(`Loan rejected successfully: ${loan.id}`);
        // Optionally, refresh the loan list to reflect the updated status
        fetchAllLoans();
    } catch (error) {
        console.error('Error rejecting loan:', error);
        alert('Failed to reject loan. Please try again.');
    }
}

// Update loan status in the backend
async function updateLoanStatus(loanId, status) {
    try {
        const response = await fetch(`/admin/loans/${loanId}/status?status=${status}`, {
            method: 'PUT' // Use PUT if your backend expects it
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`Error updating loan status: ${errorText}`);
            alert(`Failed to update the loan status. Please try again.`);
            return;
        }

        alert(`Loan ${status.toLowerCase()} successfully!`);
    } catch (error) {
        console.error(`Error in updateLoanStatus function:`, error);
        alert(`An error occurred while trying to update the loan status.`);
    }
}

// Update loan history and send notifications
async function updateLoanHistoryAndNotify(loanId, status) {
    // 1. Fetch the updated loan history (based on the name associated with the loanId)
    const loanName = await getLoanNameById(loanId); // You need to implement this function or use a service
    fetchLoanHistoryByName(loanName); // Refresh the loan history

    // 2. Notify the user about the status change
    const notificationMessage = `Your loan application has been ${status.toLowerCase()} by the admin.`;
    addNotification(loanName, notificationMessage); // You need to implement this notification system
}

// Fetch loan name by ID (or use existing service to get the name)
async function getLoanNameById(loanId) {
    try {
        const response = await fetch(`/api/loans/${loanId}`);
        if (!response.ok) throw new Error("Network response was not ok");
        const loan = await response.json();
        return loan.name;  // Assuming the loan object has a 'name' property
    } catch (error) {
        console.error("Error fetching loan details:", error);
        alert("Failed to fetch loan details.");
    }
}

// Fetch loan history based on name
async function fetchLoanHistoryByName(name) {
    try {
        const response = await fetch(`/api/loans/by-name/${encodeURIComponent(name)}`);
        if (!response.ok) {
            throw new Error(`Error fetching loan history: ${response.statusText}`);
        }

        const loans = await response.json();
        const tableBody = document.getElementById("transactionTable").querySelector("tbody");
        tableBody.innerHTML = ""; // Clear existing rows

        if (loans.length > 0) {
            loans.forEach((loan) => {
                const row = document.createElement("tr");
                const formattedDate = new Date(loan.applicationDate).toLocaleString();
                row.innerHTML = `
                    <td>${loan.name}</td>
                    <td>${loan.id}</td>
                    <td>$${loan.amount.toFixed(2)}</td>
                    <td>${loan.status}</td>
                    <td>${formattedDate}</td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            const row = document.createElement("tr");
            row.innerHTML = "<td colspan='5'>No transactions found</td>";
            tableBody.appendChild(row);
        }
    } catch (error) {
        console.error("Error fetching loan history:", error);
        alert("Failed to fetch loan history. Please try again later.");
    }
}

// Add a notification for the user
function addNotification(userName, message) {
    const notificationSection = document.getElementById("notificationSection");
    const notification = document.createElement("div");
    notification.classList.add("notification");
    notification.innerText = `Notification for ${userName}: ${message}`;
    notificationSection.appendChild(notification);

    // You may want to add logic to remove the notification after a few seconds
    setTimeout(() => notification.remove(), 5000);  // Remove after 5 seconds
}

// Fetch personal information
async function fetchPersonalInfo() {
    try {
        const response = await fetch('/api/personal-information/all');  // Ensure this API exists
        if (!response.ok) throw new Error('Network response was not ok');

        const personalInfoList = await response.json();
        const tableBody = document.getElementById("personalInfoTable").querySelector("tbody");
        tableBody.innerHTML = ""; // Clear existing rows

        if (Array.isArray(personalInfoList) && personalInfoList.length > 0) {
            personalInfoList.forEach(info => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${info.id}</td>
                    <td>${info.fullName}</td>
                    <td>${new Date(info.dateOfBirth).toLocaleDateString()}</td> <!-- Formatting Date of Birth -->
                    <td>${info.gender}</td>
                    <td>${info.civilStatus}</td>
                    <td>${info.email}</td>
                    <td>${info.phone}</td>
                    <td>${info.address}</td>
                    <td>
                        <form action="/admin/delete-user/${info.id}" method="post" style="display:inline;">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            const row = document.createElement("tr");
            row.innerHTML = "<td colspan='9'>No personal information available</td>";
            tableBody.appendChild(row);
        }
    } catch (error) {
        console.error("Error fetching personal information:", error);
        alert("Failed to fetch personal information.");
    }
}
function confirmDelete(userId) {
    if (confirm("Are you sure you want to delete this user?")) {
        fetch(`/admin/delete-user/${userId}`, { 
            method: "DELETE" // Use DELETE instead of POST
        })
        .then(response => {
            if (response.ok) {
                alert("User deleted successfully!");
                fetchPersonalInfo(); // Refresh table after deletion
            } else {
                return response.text().then(text => { throw new Error(text); });
            }
        })
        .catch(error => {
            console.error("Error deleting user:", error);
            alert("Failed to delete user. Please try again later.");
        });
    }
}