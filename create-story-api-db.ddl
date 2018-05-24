create sequence ACCEPTANCE_CRITERION_ID_SEQ start 1 increment 1;
create sequence story_id_seq start 1 increment 1;
create table ACCEPTANCE_CRITERION (ACCEPTANCE_CRITERION_ID int4 not null, DESCRIPTION varchar(255) not null, STORY_ID int4 not null, primary key (ACCEPTANCE_CRITERION_ID));
create table MESSAGE_EVENT (TOPIC_ID varchar(255) not null, MESSAGE_REFERENCE varchar(255) not null, VERSION int4, primary key (TOPIC_ID));
create table SPRINT (SPRINT_ID int4 not null, PROJECT_ID int4 not null, primary key (SPRINT_ID));
create table STORY (STORY_ID int4 not null, DESCRIPTION varchar(255) not null, PROJECT_ID int4 not null, SPRINT_ID int4, STATUS varchar(255) not null, STORY_POINTS int4 not null, TITLE varchar(255) not null, primary key (STORY_ID));
create table TASK (TASK_ID int4 not null, STATUS varchar(255) not null, STORY_ID int4 not null, primary key (TASK_ID));
alter table ACCEPTANCE_CRITERION add constraint ACCPT_CRITERION_TO_STORY_FK foreign key (STORY_ID) references STORY;
alter table TASK add constraint TASK_TO_STORY_FK foreign key (STORY_ID) references STORY;
