import requests
import threading

url = "http://localhost:8081/api/v1/transaction/withdrawal"
headers = {"Content-Type": "application/json"}

def send_request(amount, account_id):
    payload = {"amount": amount, "accountId": account_id}
    response = requests.post(url, json=payload, headers=headers)
    print(f"İstek ({amount}): {response.status_code}")
    if response.status_code == 200:
        print(response.json())
    else:
        print(response.text)

t1 = threading.Thread(target=send_request, args=(70, 2))
t2 = threading.Thread(target=send_request, args=(35, 2))

t1.start()
t2.start()

t1.join()
t2.join()