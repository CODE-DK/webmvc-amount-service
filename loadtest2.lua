wrk.method = "POST"
wrk.headers["content-type"] = "application/json"
wrk.body = "{ \"fromAccountId\": 2, \"toAccountId\": 1, \"amount\": 1 }"