# Papelaria Ideal - Colégio Santa Fé

## Instruções para subir o MySQL

1. Abra o terminal e digite => **docker build -t papelaria/santa-fe-mysql .** (incluíndo o ponto no final);
2. Em seguida, suba o container com o comando => **docker run -d --name papelaria-database -p 3306:3306 papelaria/santa-fe-mysql**;
3. Verifique se o container subiu normalmente com o comando => **docker ps**;