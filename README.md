cqlsh> CREATE KEYSPACE tutorials_keyspace WITH REPLICATION = {'class' : 'SimpleStrategy','replication_factor' : 1};
cqlsh> use tutorials_keyspace;
cqlsh:tutorials_keyspace> drop keyspace tutorial_keyspace;
cqlsh:tutorials_keyspace> create table tutorials(id int primary key, name text, number text, amount double);
cqlsh:tutorials_keyspace> drop table tutorials_keyspace.tutorials;
cqlsh:tutorials_keyspace> select * from tutorials;

PRIMARY KEY (id, title)

rohit@LTPBAN292530540:~$ sudo nano /etc/cassandra/cassandra.yaml
rohit@LTPBAN292530540:~$ sudo service cassandra restart
rohit@LTPBAN292530540:~$ sudo service cassandra status
https://phoenixnap.com/kb/install-cassandra-on-ubuntu
https://www.youtube.com/watch?v=Tjh8Yoc6xzw
https://www.youtube.com/watch?v=77w_i6BzFDU