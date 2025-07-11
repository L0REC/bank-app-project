
/*API base URL*/
const API_BASE = '/api';

/*quick deposit function*/
async function quickDeposit() {
	try {
		const response = await fetch(`${API_BASE}/deposit`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({ amount: 10.0 })
		});

		const result = await response.json();

		if (result.success) {
			alert(`Success: ${result.message}\nNew Balance: $${result.balance.toFixed(2)}`);
			location.reload();
		} else {
			alert(`Error: ${result.message}`);
		}
	} catch (error) {
		alert('Error: Failed to process deposit');
		console.error('Deposit error:', error);
	}
}
/*quick withdraw function*/
async function quickWithdraw() {
	try {
		const response = await fetch(`${API_BASE}/withdraw`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({ amount: 5.0 })
		});

		const result = await response.json();

		if (result.success) {
			alert(`Success: ${result.message}\nNew Balance: $${result.balance.toFixed(2)}`);
			location.reload();
		} else {
			alert(`Error: ${result.message}`);
		}
	} catch (error) {
		alert('Error: Failed to process withdrawal');
		console.error('Withdrawal error:', error);
	}
}

/*check balance function*/
async function checkBalance() {
	try {
		const response = await fetch(`${API_BASE}/balance`);
		const result = await response.json();

		alert(`Current Balance: $${result.balance.toFixed(2)}`);
	} catch (error) {
		alert('Error: Failed to check balance');
		console.error('Balance check error:', error);
	}
}

/*API testing functions*/
async function testAllEndpoints() {
	console.log('Testing all API endpoints...');

	/*test balance check*/
	const balanceResponse = await fetch(`${API_BASE}/balance`);
	console.log('Balance check:', await balanceResponse.json());

	/*test deposit*/
	const depositResponse = await fetch(`${API_BASE}/deposit`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ amount: 25.0 })
	});
	console.log('Deposit test:', await depositResponse.json());

	/*test withdrawal*/
	const withdrawResponse = await fetch(`${API_BASE}/withdraw`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ amount: 10.0 })
	});
	console.log('Withdraw test:', await withdrawResponse.json());

	/*test account details*/
	const accountResponse = await fetch(`${API_BASE}/account`);
	console.log('Account details:', await accountResponse.json());
}
/*realtime balance updates*/
let currentBalance = 0;

/*update balance display*/
function updateBalanceDisplay(balance) {
	const balanceElements = document.querySelectorAll('.balance-display');
	balanceElements.forEach(element => {
		element.textContent = `${balance.toFixed(2)}`;
	});
	currentBalance = balance;
}

/*enhanced deposit with realtime feedback*/
async function enhancedDeposit(amount) {
	const depositBtn = document.getElementById('depositBtn');
	if (depositBtn) {
		depositBtn.disabled = true;
		depositBtn.textContent = 'Processing...'
	}

	try {
		const response = await fetch(`${API_BASE}/deposit}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({ amount: parseFloat(amount) })
		});

		const result = await response.json();

		if (result.success) {
			updateBalanceDisplay(result.balance);
			showNotification('success', result.message);
			updateTransactionHistory();
		} else {
			showNotification('error', result.message);
		}
	} catch (error) {
		showNotification('error', 'Failed to process deposit');
		console.error('Deposit error:', error);
	} finally {
		if (depositBtn) {
			depositBtn.disabled = false;
			depositBtn.textContent = 'Deposit';
		}
	}
}

/*show notification*/
function showNotification(type, message) {
	const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
	const notification = document.createElement('div');
	notification.className = `alert ${alertClass} alert-dismissible fade show`;
	notification.innerHTML = `${message} <button type="button" class="btn-close" data-bs-dismiss="alert"></button>`;

	const container = document.querySelector('.container');
	container.insertBefore(notification, container.firstChild);

	/*auto remove after 5 seconds*/
	setTimeout(() => {
		notification.remove();
	}, 5000);
}

/*auto refresh balance every 30 seconds*/
setInterval(async () => {
	try {
		const response = await fetch(`${API_BASE}/balance`);
		const result = await response.json();
		updateBalanceDisplay(result.balance);
	} catch (error) {
		console.error('Auto refresh error:', error);
	}
}, 30000);






















