INSERT INTO authorities VALUES (1,'user',b'1','ROLE_USER'),(2,'admin',b'1','ROLE_ADMIN'),(3,'user',b'1','ROLE_ADMIN');
INSERT INTO resources VALUES (1,'user path',b'1','user\'s home',1,'/user/**'),(2,'admin path',b'1','admin\'s home',1,'/admin/**'),(3,'root path',b'1','root',1,'/*');
INSERT INTO role VALUES (1,'user','','ROLE_USER'),(2,'admin','','ROLE_ADMIN');
INSERT INTO user VALUES (1,'admins home',22,'11111111111','lopoo1@qq.com',b'1','admin','14e1b600b1fd579f47433b88e8d85291','2014-05-22 17:06:51','admin',NULL),(2,NULL,0,NULL,'leipeng12@gmail.com',b'1','leipeng','14e1b600b1fd579f47433b88e8d85291','2014-05-23 10:56:28','lopoo' , NULL);
INSERT INTO user_role VALUES (1,2),(2,1);
INSERT INTO authority_resource VALUES (2,2),(1,1),(2,3);
INSERT INTO role_authority VALUES (1,1),(2,2),(1,3);
INSERT INTO user_group VALUES (1,'1111111111111111111111111111111','home',1);
UPDATE user SET user_group_id=1 where id=1 or id=2;
INSERT INTO arti VALUES (1,'010203040506','name','first',1);
INSERT INTO node VALUES (1,'node1',b'0',b'0','123456','power1','0001',b'0',1);
INSERT INTO node VALUES (2,'node2',b'0',b'0','123457','power2','0002',b'0',1);
INSERT INTO node VALUES (3,'node3',b'0',b'0','123458','power3','0003',b'0',1);
INSERT INTO node VALUES (4,'node4',b'0',b'0','123459','power4','0004',b'0',1);
INSERT INTO node VALUES (5,'node5',b'0',b'0','123450','power5','0005',b'0',1);

