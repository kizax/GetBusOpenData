�d�߬Y�Ӯɬq���������
SELECT * FROM busdb.busdata WHERE datatime >= '2016-02-10 13:58:00' AND datatime < '2016-02-10 13:59:00';

�d��266���������
�]�t266(�_��-����)��266
266%�����O266�}�Y��routename 
like�|�i��r����
�e���n���[�W�ɶ�����d�߽d�� �_�h�d�߮ɶ��|�ܤ[
SELECT * FROM busdb.busdata WHERE datatime >= '2016-02-10 13:58:00' AND datatime < '2016-02-10 13:59:00' AND routename LIKE '266%';

�d��349-FP���������
�ϥ�'='�d��
SELECT * FROM busdb.busdata WHERE datatime >= '2016-02-10 13:58:00' AND datatime < '2016-02-10 13:59:00' AND busid = '349-FP';

�o�ɭԵo�{�r�������C�A��ĳ�H�Pbusid�۹�����carid�Ӱ��d�ߡA�|�`�٤@�I�ɶ�
SELECT * FROM busdb.busdata WHERE datatime >= '2016-02-10 13:58:00' AND datatime < '2016-02-10 13:59:00' AND carid = '222233749';