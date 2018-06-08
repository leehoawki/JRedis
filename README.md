# JRedis
JRedis is an in-memory database that persists on disk which can work with common Redis client.

# INSTALL

    mvn clean package
    docker build . -t jredis
    docker run -d -p 9000:9000 jredis

# Benchmark

    P:\>redis-benchmark -p 6379 get
    ====== get ======
      100000 requests completed in 1.38 seconds
      50 parallel clients
      3 bytes payload
      keep alive: 1

    97.60% <= 1 milliseconds
    99.97% <= 2 milliseconds
    100.00% <= 2 milliseconds
    72568.94 requests per second

    P:\>redis-benchmark -p 9000 get
    ====== get ======
      100000 requests completed in 4.80 seconds
      50 parallel clients
      3 bytes payload
      keep alive: 1

    8.17% <= 1 milliseconds
    97.33% <= 2 milliseconds
    99.93% <= 3 milliseconds
    100.00% <= 4 milliseconds
    20824.66 requests per second