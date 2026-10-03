# Performance Report

## 1. Target Query
db.events.find({userId: 'user_001'}).sort({timestamp: -1})

## 2. Before Index
COLLSCAN - 100,000 documents examined.

## 3. Index Creation
db.events.createIndex({userId: 1, timestamp: -1})

## 4. After Index
IXSCAN - Only relevant documents examined.

## 5. Conclusion
The compound index eliminates full collection scans and optimizes query performance.
