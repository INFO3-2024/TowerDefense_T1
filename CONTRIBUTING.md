# Como contribuir para o desenvolvimento

## Pré-requisitos
- Java 8 ou superior
- Git

## Primeiros passos

1. Clonar o repositório:
```bash
  git clone https://github.com/INFO3-2024/TowerDefense_T1
```
2. Abra o projeto na sua IDE (No caso abaixo é o VSCode):
```bash
  code TowerDefense_T1
```

3. Comece a desenvolver

## Nomenclatura de arquivos
- Classes e Interfaces: PascalCase (Ex: GiantEnemy)
- Funções e variáveis: camelCase (Ex: setPosition() ou isColliding)
- Nome dos assets: camelCase(Ex: enemySprite ou playerFrame)

Todas as classes, funções e variáveis devem tem ser nome escrito em inglês

## Regras dentro do repositório

> **SEM COMMITAR DIRETO NA MAIN**.
>
> Os códigos devem ir para ela somente quando as mudanças ou adições estiverem concluidas e testadas.

A pedido da equipe de organização, quando alguma nova funcionalidade ou resolução de bug tiver que ser feita, pedimos para criarem uma nova branch no repositório, seguindo o seguinte padrão:

```
funcionalidade/nova-funcionalidade
```
```
fix/causa-do-bug
```
Após trabalharem nas branches e já terem feitos os commits é só abrir um pull request que a equipe de organização vai avaliar e aceitar seu código na branch main
