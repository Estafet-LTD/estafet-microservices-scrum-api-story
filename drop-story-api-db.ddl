alter table ACCEPTANCE_CRITERION drop constraint FKk57pmvcvti3wttp81pio3b4ag;
alter table TASK drop constraint FK2fjocyiwq3pnx8ihjw323nex0;
drop table if exists ACCEPTANCE_CRITERION cascade;
drop table if exists MESSAGE_EVENT cascade;
drop table if exists SPRINT cascade;
drop table if exists STORY cascade;
drop table if exists TASK cascade;
drop sequence ACCEPTANCE_CRITERION_ID_SEQ;
drop sequence story_id_seq;
