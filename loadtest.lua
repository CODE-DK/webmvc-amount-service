wrk.method = "POST"
wrk.headers["content-type"] = "application/json"
wrk.body = "{ \"fromAccountId\": 1, \"toAccountId\": 2, \"amount\": 1 }"