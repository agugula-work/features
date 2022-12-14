INSERT INTO USER_(name, password) values ('arkadiusz.gugula', '$2a$11$R9adNlzXML7Tva7w/NxKX.a9B2smPDiRcLdpUcgIuiQtFbCP8GnaS');
INSERT INTO USER_(name, password) values ('bill.gates', '$2a$11$R9adNlzXML7Tva7w/NxKX.a9B2smPDiRcLdpUcgIuiQtFbCP8GnaS');
INSERT INTO USER_(name, password) values ('jeff.bezos', '$2a$11$R9adNlzXML7Tva7w/NxKX.a9B2smPDiRcLdpUcgIuiQtFbCP8GnaS');
INSERT INTO USER_(name, password) values ('mark.zuckerberg', '$2a$11$R9adNlzXML7Tva7w/NxKX.a9B2smPDiRcLdpUcgIuiQtFbCP8GnaS');
INSERT INTO USER_(name, password) values ('admin', '$2a$11$R9adNlzXML7Tva7w/NxKX.a9B2smPDiRcLdpUcgIuiQtFbCP8GnaS');


INSERT INTO PERMISSION(name, global) values ('feature-flags_view-all', false);
INSERT INTO PERMISSION(name, global) values ('feature-flags_add', false);
INSERT INTO PERMISSION(name, global) values ('feature-flags_delete', false);
INSERT INTO PERMISSION(name, global) values ('feature-global-permission', true);
INSERT INTO PERMISSION(name, global) values ('admin', false);

INSERT INTO USER_2_PERMISSION(U2P_U_ID, U2P_P_ID) values (1, 1);
INSERT INTO USER_2_PERMISSION(U2P_U_ID, U2P_P_ID) values (1, 2);
INSERT INTO USER_2_PERMISSION(U2P_U_ID, U2P_P_ID) values (2, 1);
INSERT INTO USER_2_PERMISSION(U2P_U_ID, U2P_P_ID) values (3, 4);
INSERT INTO USER_2_PERMISSION(U2P_U_ID, U2P_P_ID) values (5, 5);
