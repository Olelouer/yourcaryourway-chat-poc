USE yourcarway_chat_poc;

-- Utilisateurs de test
INSERT INTO users (email, first_name, last_name) VALUES
('alice@example.com', 'Alice', 'Dupont'),
('bob@example.com', 'Bob', 'Martin');

-- Agents de test
INSERT INTO agents (email, first_name, last_name) VALUES
('sophie@yourcarway.com', 'Sophie', 'Lefebvre'),
('thomas@yourcarway.com', 'Thomas', 'Bernard');

-- Quelques messages de test (optionnel)
INSERT INTO chat_messages (user_id, agent_id, sender_type, message, sent_at) VALUES
(1, 1, 'user', 'Bonjour, j''ai une question sur ma réservation.', NOW() - INTERVAL 5 MINUTE),
(1, 1, 'agent', 'Bonjour Alice, je suis là pour vous aider !', NOW() - INTERVAL 4 MINUTE),
(1, 1, 'agent', 'Quelle est votre question ?', NOW() - INTERVAL 4 MINUTE);