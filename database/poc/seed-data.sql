USE yourcaryourway_chat_poc;

-- Test users
INSERT INTO users (email, first_name, last_name) VALUES
('alice@example.com', 'Alice', 'Dupont'),
('bob@example.com', 'Bob', 'Martin');

-- Test agent
INSERT INTO agents (email, first_name, last_name) VALUES
('sophie@yourcarway.com', 'Sophie', 'Lefebvre');

-- Optional - Some test conversations and messages 
INSERT INTO chat_conversations (user_id, agent_id, status) VALUES
(1, 1, 'pending_user');

INSERT INTO chat_messages (conversation_id, sender_type, message, sent_at) VALUES
(1, 'user', 'Bonjour, j''ai une question sur ma réservation.', NOW() - INTERVAL 5 MINUTE),
(1, 'agent', 'Bonjour Alice, je suis là pour vous aider !', NOW() - INTERVAL 4 MINUTE),
(1, 'agent', 'Quelle est votre question ?', NOW() - INTERVAL 4 MINUTE);