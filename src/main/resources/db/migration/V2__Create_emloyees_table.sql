create table EMPLOYEE
(
    ID            NUMBER generated as identity
        constraint PK_EMPLOYEE
            primary key,
    NAME          VARCHAR2(1000) not null,
    DEPARTMENT_ID NUMBER         not null,
    VERSION       NUMBER         not null,
    CREATED_AT    TIMESTAMP(6)   not null,
    UPDATED_AT    TIMESTAMP(6)   not null
)

