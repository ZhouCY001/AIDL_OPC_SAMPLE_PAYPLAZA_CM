# OPC_AIDL_SAMPLE_PAYPLAZA

AIDL sample project for calling the Payplaza payment application.

## Update History

### 2026/03/13
Added routing whitelist management examples through `setParam(String jsonData)`.

The `setParam` button in the sample now supports the following routing whitelist operations:
- `Query Routing List`
- `Query Routing By PAN`
- `Add Routing`
- `Update Routing`
- `Delete Routing`
- `Clear Routing`
- `Reset Default Routing`
- `App Params` (existing app parameter example is retained)

#### Example payloads

Query full routing list:
```json
{
  "routingAction": "queryRoutingList"
}
```

Query matched routing item by PAN:
```json
{
  "routingAction": "queryRoutingByPan",
  "routingPan": "4875800199991234"
}
```

Add routing item:
```json
{
  "routingAction": "addRouting",
  "routingItem": {
    "name": "Test Bank",
    "brand": "Visa Debit",
    "binStart": "48758001"
  }
}
```

Update routing item:
```json
{
  "routingAction": "updateRouting",
  "routingItem": {
    "id": 1,
    "name": "Test Bank Updated",
    "brand": "Visa Debit",
    "binStart": "48758001"
  }
}
```

Delete routing item:
```json
{
  "routingAction": "deleteRouting",
  "routingId": 1
}
```

Clear routing table:
```json
{
  "routingAction": "clearRouting"
}
```

Reset default routing table:
```json
{
  "routingAction": "resetDefaultRouting"
}
```

#### Notes
- `binStart` supports 6 to 10 digits.
- Overlapping BIN prefixes are allowed.
- Matching follows the longest-prefix-first rule.
- The response JSON may include `RespCode`, `RespDesc`, `routingItem`, `routingList`, and `routingCount`.

### 2025/10/16
Added `merchantOrderRef`.

If this field is provided, it will be used as the value of `5FE341` and sent to the backend.
