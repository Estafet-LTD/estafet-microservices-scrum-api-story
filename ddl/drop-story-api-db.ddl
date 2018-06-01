alter table ACCEPTANCE_CRITERION drop constraint ACCPT_CRITERION_TO_STORY_FK;
alter table TASK drop constraint TASK_TO_STORY_FK;
drop table if exists ACCEPTANCE_CRITERION cascade;
drop table if exists MESSAGE_EVENT cascade;
drop table if exists SPRINT cascade;
drop table if exists STORY cascade;
drop table if exists TASK cascade;
drop sequence ACCEPTANCE_CRITERION_ID_SEQ;
drop sequence story_id_seq;
