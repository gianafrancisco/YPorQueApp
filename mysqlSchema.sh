service mysql start && \
echo "create database yporque" >/tmp/database && \
mysql -u root --password=toor < /tmp/database

