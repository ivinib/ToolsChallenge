INSERT INTO tb_descricao (valor, data_hora, estabelecimento, nsu, codigo_autorizacao, status) VALUES ('123.45', '2025-10-31 12:00:00', 'Mercado Compra Certa', '1234567890', '1234567890', 'AUTORIZADO');
INSERT INTO tb_forma_pagamento( tipo, parcelas) VALUES ('AVISTA', '1');
INSERT INTO tb_transacao(cartao, id_descricao, id_forma_pagamento) VALUES ('1234*****2345', 1, 1);

INSERT INTO tb_descricao (valor, data_hora, estabelecimento, nsu, codigo_autorizacao, status) VALUES ('499.90', '2025-10-30 15:00:00', 'Compra Certa', '1234567890', '1234567890', 'AUTORIZADO');
INSERT INTO tb_forma_pagamento( tipo, parcelas) VALUES ('PARCELADO_EMISOR', '3');
INSERT INTO tb_transacao(cartao, id_descricao, id_forma_pagamento) VALUES ('1234*****2345', 2, 2);

INSERT INTO tb_descricao (valor, data_hora, estabelecimento, nsu, codigo_autorizacao, status) VALUES ('2547.74', '2025-10-27 12:00:00', 'EShop', '1234567890', '1234567890', 'NEGADO');
INSERT INTO tb_forma_pagamento( tipo, parcelas) VALUES ('PARCELADO_LOJA', '12');
INSERT INTO tb_transacao(cartao, id_descricao, id_forma_pagamento) VALUES ('1234*****2345', 3, 3);

INSERT INTO tb_descricao (valor, data_hora, estabelecimento, nsu, codigo_autorizacao, status) VALUES ('1049.87', '2025-10-29 10:10:00', 'InfoTec', '1234567890', '1234567890', 'CANCELADO');
INSERT INTO tb_forma_pagamento( tipo, parcelas) VALUES ('AVISTA', '1');
INSERT INTO tb_transacao(cartao, id_descricao, id_forma_pagamento) VALUES ('1234*****2345', 4, 4);
