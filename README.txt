Identificação do Problema: Caixeiro Viajante
Problema NP. Necessário escolher uma Heurística de solução.
Devido a limitações computacionais abre-se mão da solução "perfeita" para todos os casos,
por uma solução viável em 100% dos casos, alguns cenários de mapas muito grandes podem
nunca chegar ao fim da execução caso não seja adotada esta estratégia.

Serão utilizada a Linguagem Java para desenvolvimento da solução, Maven para facilitar
no controle de dependências e Tomcat para ser o nosso servidor.
As escolhas se pautam pela simplicidade, logo, serão usados os seguites frameworks:
- Jersey: Para expor os Webservices;
- Spring: Para fazer a injeção de depêndencia;
- GSON: para simplificar a serialização e deserialização dos Objetos;
- JUnit: para realização de testes automáticos.
Outras decisões:
- Persitência em Arquivo: Para persistir as rotas entre uma execução e outra.
- Local do arquivo: C:/Dados/mapas.json
- Versão do Java: 1.7, por ter sido usado NIO2 para acessar o arquivo.
- Maven versão 3.0.4 ou superior, para gestão de dependencias.

JSon exemplo - Novo Mapa:
{"name":"test","roads":[{"destination":"B","distance":10,"source":"A"},{"destination":"D",
"distance":15,"source":"B"},{"destination":"C","distance":20,"source":"A"},{"destination":
"D","distance":30,"source":"C"},{"destination":"E","distance":50,"source":"B"},
{"destination":"E","distance":30,"source":"D"}]}

Algoritmo para Cálculo da rota segue os seguintes passos:
- A partir do nó X, identificar o Nó Y mais próximo do atual e formar o Arco XY;
- Em seguida, Identificar o nó Z mais próximo de Y, que não faça parte do caminho;
- Repetir os passos até que cheguemos ao destino, ou fiquemos "Sem Saída".
- Caso cheguemos ao destino, validar para cada conjunto de arcos se existe um arco XY  
com menor custo do que o conjunto de arcos a ser subtituído, repetir esse processo 3x.
- No caso de ficar "Sem Saída", voltaremos 1 passo atrás, escolhendo o Segundo mais
próximo, voltando sucessivamente até encontrar o destino. Feito isso, aplicaremos a
otimização de acima. 
- Além de fazer o caminho, também será feito o caminho de volta, a melhor opção será a escolhida.
