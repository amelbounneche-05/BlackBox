# Architectural Model: Document Schema for Pigeon Logs

## 1. Objectives of the Document Model

- **Flexibility & Heterogeneity:** Store diverse event structures (logins, payments, errors, API calls, notifications) within a single collection (`events`) without rigid relational constraints.

- **Performance in Read & Write:**
  - _Append-only writes:_ High-speed continuous log ingestion.
  - _Optimized reads:_ Event-specific details are embedded so that a single document fetch contains its full context, avoiding costly `$lookup` joins during aggregation pipelines.

- **Cross-Cutting Searches:** Standardize global analytics and filtering across any event type using shared common fields (`eventId`, `eventType`, `timestamp`, `userId`).

- **Volume Management:** Avoid data redundancy (e.g., user profiles) by using ID referencing (`userId`), ensuring scalable growth over a simulated one-year period (100,000+ documents).

---

## 2. Common Fields Across All Events

Every document in the `events` collection shares this standard base structure:

- `_id`: ObjectId (MongoDB generated unique identifier)
- `eventId`: String (UUID uniquely identifying the specific event occurrence)
- `eventType`: String (The category/type of the event)
- `timestamp`: ISODate (Exact date and time when the event occurred)
- `userId`: String (Unique identifier linking the event to a user, or `null` for anonymous/system events)

---

## 3. Event Types, Structures & JSON Examples

### A. USER_LOGIN

Tracks user authentication attempts.

- Specific fields: `ipAddress` (String), `userAgent` (String), `success` (Boolean)

```json
{
  "_id": { "$oid": "65e4b2c1f1a2b3c4d5e6f7a1" },
  "eventId": "e3b0c442-de91-4nf9-8800-4b2320b5f101",
  "eventType": "USER_LOGIN",
  "timestamp": { "$date": "2026-06-15T08:30:00Z" },
  "userId": "user_98765",
  "ipAddress": "192.168.1.45",
  "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
  "success": true
}

B. SUBSCRIPTION_PAYMENT

Tracks financial transactions related to subscriptions.
. Specific fields: amount (Number), currency (String), paymentMethod (String), invoiceId (String)

{
  "_id": { "$oid": "65e4b2c1f1a2b3c4d5e6f7a2" },
  "eventId": "f4c1d553-ef02-5og0-9911-5c3431c6g202",
  "eventType": "SUBSCRIPTION_PAYMENT",
  "timestamp": { "$date": "2026-06-15T09:15:22Z" },
  "userId": "user_98765",
  "amount": 29.99,
  "currency": "EUR",
  "paymentMethod": "CREDIT_CARD",
  "invoiceId": "INV-2026-0891"
}

C. APP_ERROR

Captures software exceptions and application failures.
. Specific fields: errorCode (String), stackTrace (String), component (String)

{
  "_id": { "$oid": "65e4b2c1f1a2b3c4d5e6f7a3" },
  "eventId": "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d",
  "eventType": "APP_ERROR",
  "timestamp": { "$date": "2026-06-15T10:45:10Z" },
  "userId": "user_12345",
  "errorCode": "ERR_NULL_POINTER",
  "stackTrace": "java.lang.NullPointerException: Cannot read field 'id' because 'session' is null\n\tat com.pigeon.service.AuthService.validate(AuthService.java:42)",
  "component": "AuthService"
}

D. API_CALL

Monitors calls made to the public API.
. Specific fields: endpoint (String), method (String), statusCode (Integer), responseTimeMs (Long)

{
  "_id": { "$oid": "65e4b2c1f1a2b3c4d5e6f7a4" },
  "eventId": "7c8b9a0f-1e2d-3c4b-5a6f-7e8d9c0b1a2f",
  "eventType": "API_CALL",
  "timestamp": { "$date": "2026-06-15T11:00:05Z" },
  "userId": "user_45678",
  "endpoint": "/api/v1/messages",
  "method": "POST",
  "statusCode": 201,
  "responseTimeMs": 142
}

E. NOTIFICATION_SENT

Tracks communication dispatches sent to users.

. Specific fields: channel (String), templateId (String), status (String)

{
  "_id": { "$oid": "65e4b2c1f1a2b3c4d5e6f7a5" },
  "eventId": "3d4c5b6a-7f8e-9d0c-1b2a-3f4e5d6c7b8a",
  "eventType": "NOTIFICATION_SENT",
  "timestamp": { "$date": "2026-06-15T11:02:30Z" },
  "userId": "user_45678",
  "channel": "EMAIL",
  "templateId": "tpl_welcome_verification",
  "status": "SENT"
}

4. Justification of Embedding and Referencing Choices
Embedding Choice: Event-Specific Payloads

. Implementation: Specific attributes for each event type are directly embedded inside the log document.

. Justification: Event logs are self-contained records read primarily for aggregated analytics. Embedding these attributes ensures a single document fetch without costly $lookup joins, optimizing performance while keeping document sizes well below MongoDB’s 16 MB limit.

Referencing Choice: User Association (userId)

. Implementation: Users are linked strictly via a userId reference rather than embedding full user profiles into every single log entry.

. Justification: Active users generate thousands of events over time. Embedding user profiles would cause massive data redundancy and storage bloating, and updating user details would require updating historical logs. A lightweight reference keeps documents lean while preserving user-centric aggregations.

```
