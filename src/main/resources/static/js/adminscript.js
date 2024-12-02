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

// Fetch all loans for the admin
async function fetchAllLoans() {
    try {
        const response = await fetch('/admin/loans'); // API for admin to fetch all loans
        const loans = await response.json();

        const tableBody = document.getElementById("adminLoanTable").querySelector("tbody");
        tableBody.innerHTML = ""; // Clear existing rows

        if (loans.length > 0) {
            loans.forEach(loan => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${loan.userId}</td>
                    <td>${loan.id}</td>
                    <td>${loan.amount.toFixed(2)}</td>
                    <td>${loan.status}</td>
                    <td>${loan.applicationDate}</td>
                    <td>
                        <button onclick="approveLoan(${loan.id})">Approve</button>
                        <button onclick="rejectLoan(${loan.id})">Reject</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            const row = document.createElement("tr");
            row.innerHTML = "<td colspan='6'>No loan applications found</td>";
            tableBody.appendChild(row);
        }
    } catch (error) {
        console.error("Error fetching all loans:", error);
        alert("Failed to fetch loan applications.");
    }
}

// Approve loan
async function approveLoan(loanId) {
    try {
        const response = await fetch(`/admin/loan/${loanId}/approve`, {
            method: 'POST'
        });

        if (response.ok) {
            alert("Loan approved successfully.");
            fetchAllLoans();  // Refresh the loan list
        } else {
            alert("Failed to approve the loan.");
        }
    } catch (error) {
        console.error("Error approving loan:", error);
        alert("Error approving the loan.");
    }
}

// Reject loan
async function rejectLoan(loanId) {
    try {
        const response = await fetch(`/admin/loan/${loanId}/reject`, {
            method: 'POST'
        });

        if (response.ok) {
            alert("Loan rejected successfully.");
            fetchAllLoans();  // Refresh the loan list
        } else {
            alert("Failed to reject the loan.");
        }
    } catch (error) {
        console.error("Error rejecting loan:", error);
        alert("Error rejecting the loan.");
    }
}
async function fetchPersonalInfo() {
    try {
        const response = await fetch('/api/personal-information/all');  // Ensure this API exists
        const personalInfoList = await response.json();

        if (response.ok && Array.isArray(personalInfoList)) {
            const tableBody = document.getElementById("personalInfoTable").querySelector("tbody");
            tableBody.innerHTML = ""; // Clear existing rows

            if (personalInfoList.length > 0) {
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
        } else {
            console.error("Failed to fetch personal information or response not valid");
            alert("Failed to fetch personal information.");
        }
    } catch (error) {
        console.error("Error fetching personal information:", error);
        alert("Failed to fetch personal information.");
    }
}
