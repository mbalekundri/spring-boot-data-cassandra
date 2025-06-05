cqlsh:tutorials_keyspace> drop keyspace tutorial_keyspace;
cqlsh> CREATE KEYSPACE tutorial_keyspace WITH REPLICATION = {'class' : 'SimpleStrategy','replication_factor' : 1};
cqlsh> use tutorial_keyspace;
cqlsh:tutorials_keyspace> CREATE TABLE IF NOT EXISTS tutorial ( id UUID PRIMARY KEY, title text, description text, published boolean);
cqlsh:tutorials_keyspace> CREATE INDEX ON tutorial(title);
cqlsh:tutorials_keyspace> drop table tutorials_keyspace.tutorial;
cqlsh:tutorials_keyspace> select * from tutorial;

PRIMARY KEY (id, title)

rohit@LTPBAN292530540:~$ sudo nano /etc/cassandra/cassandra.yaml
rohit@LTPBAN292530540:~$ sudo service cassandra restart
rohit@LTPBAN292530540:~$ sudo service cassandra status
https://phoenixnap.com/kb/install-cassandra-on-ubuntu
https://www.youtube.com/watch?v=Tjh8Yoc6xzw
https://www.youtube.com/watch?v=77w_i6BzFDU