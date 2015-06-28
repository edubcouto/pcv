Identifica��o do Problema: Caixeiro Viajante
Problema NP. Necess�rio escolher uma Heur�stica de solu��o.
Devido a limita��es computacionais abre-se m�o da solu��o "perfeita" para todos os casos,
por uma solu��o vi�vel em 100% dos casos, alguns cen�rios de mapas muito grandes podem
nunca chegar ao fim da execu��o caso n�o seja adotada esta estrat�gia.

Ser�o utilizada a Linguagem Java para desenvolvimento da solu��o, Maven para facilitar
no controle de depend�ncias e Tomcat para ser o nosso servidor.
As escolhas se pautam pela simplicidade, logo, ser�o usados os seguites frameworks:
- Jersey: Para expor os Webservices;
- Spring: Para fazer a inje��o de dep�ndencia;
- GSON: para simplificar a serializa��o e deserializa��o dos Objetos;
- JUnit: para realiza��o de testes autom�ticos.
Outras decis�es:
- Persit�ncia em Arquivo: Para persistir as rotas entre uma execu��o e outra.
- Local do arquivo: C:/Dados/mapas.json
- Vers�o do Java: 1.7, por ter sido usado NIO2 para acessar o arquivo.
- Maven vers�o 3.0.4 ou superior, para gest�o de dependencias.

JSon exemplo - Novo Mapa:
{"name":"test","roads":[{"destination":"B","distance":10,"source":"A"},{"destination":"D",
"distance":15,"source":"B"},{"destination":"C","distance":20,"source":"A"},{"destination":
"D","distance":30,"source":"C"},{"destination":"E","distance":50,"source":"B"},
{"destination":"E","distance":30,"source":"D"}]}

Algoritmo para C�lculo da rota segue os seguintes passos:
- A partir do n� X, identificar o N� Y mais pr�ximo do atual e formar o Arco XY;
- Em seguida, Identificar o n� Z mais pr�ximo de Y, que n�o fa�a parte do caminho;
- Repetir os passos at� que cheguemos ao destino, ou fiquemos "Sem Sa�da".
- Caso cheguemos ao destino, validar para cada conjunto de arcos se existe um arco XY  
com menor custo do que o conjunto de arcos a ser subtitu�do, repetir esse processo 3x.
- No caso de ficar "Sem Sa�da", voltaremos 1 passo atr�s, escolhendo o Segundo mais
pr�ximo, voltando sucessivamente at� encontrar o destino. Feito isso, aplicaremos a
otimiza��o de acima. 
- Al�m de fazer o caminho, tamb�m ser� feito o caminho de volta, a melhor op��o ser� a escolhida.
