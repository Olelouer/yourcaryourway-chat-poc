# Your Car Your Way - POC Chat Agent/Utilisateurs

Ce POC est en développement pour tester la fonctionnalité de chat agent/utilisateurs

## 📋 Prérequis

- **Java** 21+
- **Node.js** 18+
- **MySQL** 8.0+
- **Maven** 3.9+
- **Angular CLI** 19

## 🗄️ Installation de la base de données

### Base de données POC

le schéma du POC est disponible dans :
```bash
database/poc/schema.sql
```

Des données de test sont disponibles dans :
```bash
database/poc/seed-data.sql
```

### Base de données complète

Pour référence, le schéma complet est disponible dans :
```bash
database/schema.sql
```

## 🚀 Installation Backend (Spring Boot)

1. Se positionner dans le dossier backend :
```bash
cd backend
```

2. Configurer la connexion à la base de données :
```bash
# Éditer src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/yourcaryourway_poc
spring.datasource.username=votre_user
spring.datasource.password=votre_password
```

3. Installer les dépendances et builder :
```bash
mvn clean install
```

4. Lancer l'application :
```bash
mvn spring-boot:run
```

Le backend sera accessible sur `http://localhost:8080`

## 💻 Installation Frontend (Angular)

1. Se positionner dans le dossier frontend :
```bash
cd frontend
```

2. Installer les dépendances :
```bash
npm install
```

3. Configurer l'URL du backend (si nécessaire) :
```bash
# Éditer src/app/services/chat.service.ts
private apiUrl = 'http://localhost:8080/api/chat';
```

4. Lancer l'application :
```bash
npm start
```

Le frontend sera accessible sur `http://localhost:4200`

## 📁 Structure du projet

```
.
├── backend/                 # Application Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/               # Application Angular 19
│   ├── src/
│   ├── angular.json
│   ├── package.json
│   └── ...
└── database/              # Scripts SQL
    ├── schema.sql         # Schéma production complet
    └── poc/
        ├── schema.sql     # Schéma POC
        └── seed-data.sql  # Données de test
```