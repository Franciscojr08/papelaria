# Papelaria Ideal - Colégio Santa Fé

## Instruções para instalar o docker
1. - Linux: https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-20-04-pt
2. - Windows: https://gist.github.com/sidneyroberto/5f0b837c2d27f791fc494c164d2a7d74

## Instruções para subir o MySQL

1. Abra o terminal e digite => **docker build -t papelaria/santa-fe-mysql .** (incluíndo o ponto no final);
2. Em seguida, suba o container com o comando => **docker run -d --name papelaria-database -p 3306:3306 papelaria/santa-fe-mysql**;
3. Verifique se o container subiu normalmente com o comando => **docker ps**;

## Instruções para rodar o projeto

1. Fazer as configurações do docker;
2. Ter o Maven instalado (atualizar e carregar suas dependências do pom.xml) e o Java 17;
3. Iniciar o projeto na IDE;
4. Acessar a documentação via: http://localhost:8080/swagger-ui/index.html ou http://localhost:8080/v3/api-docs
