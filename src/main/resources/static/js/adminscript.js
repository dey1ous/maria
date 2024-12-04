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

// Fetch all loans and populate the table
// Fetch all loans and populate the table
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

// Approve a loan
async function approveLoan(loanId) {
    await updateLoanStatus(loanId, 'APPROVED');
}

// Reject a loan
async function rejectLoan(loanId) {
    await updateLoanStatus(loanId, 'REJECTED');
}

// Update loan status
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
                    <td>${info.dateOfBirth}</td>
                    <td>${info.gender}</td>
                    <td>${info.civilStatus}</td>
                    <td>${info.email}</td>
                    <td>${info.phone}</td>
                    <td>${info.address}</td>
                    <td>
                        <form action="/admin/delete-user/${info.id }" method="post" style="display:inline;">
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