
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
	const withdrawalResponse = await fetch(`${API_BASE}/withdrawal`, {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ amount: 10.0 })
	});
	console.log('Withdrawal test:', await withdrawalResponse.json());

	/*test account details*/
	const accountResponse = await fetch(`${API_BASE}/account`);
	console.log('Account details:', await accountResponse.json());
}























