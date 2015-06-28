Identifica��o do Problema: Caixeiro Viajante
Problema NP. Necess�rio escolher uma Heur�stica de solu��o.
Devido a limita��es computacionais abre-se mal da solu��o "perfeita" em 100% dos casos,
por uma solu��o vi�vel em 100% dos casos, alguns cen�rios de mapas muito grandes podem
nunca chegar ao fim da execu��o caso n�o seja adotada esta estrat�gia.

Lembrando dos tempos de faculdade, decidi seguir a seguinte estrat�gia:
- A partir do n� X, identificar o N� Y mais pr�ximo do atual e formar o Arco XY;
- Em seguida, Identificar o n� Z mais pr�ximo de Y, que n�o fa�a parte do caminho;
- Repetir os passos at� que cheguemos ao destino, ou fiquemos "Sem Sa�da".
- Caso cheguemos ao destino, validar para cada Arco XY a exist�ncia de um Arco XAY 
com menor custo do que XY, repetir esse processo at� n�o existirem mais otimiza��es 
poss�veis.
- No caso de ficar "Sem Sa�da", voltaremos 1 passo atr�s, escolhendo o Segundo mais
pr�ximo, voltando sucessivamente at� encontrar o destino. Feito isso, aplicaremos a
otimiza��o de acima. 

Ser�o utilizada a Linguagem Java para desenvolvimento da solu��o, Maven para facilitar
no controle de depend�ncias e Tomcat para ser o nosso servidor.
As escolhas se pautam pela simplicidade, logo, ser�o usados os seguites frameworks:
- Jersey: Para expor os Webservices;
- GSON: para simplificar a serializa��o e deserializa��o dos Objetos;
- Arquivo: Para persistir as rotas entre uma execu��o e outra.

JSon exemplo - Novo Mapa:
{"nomeMapa":"aqui_nome","malha":[{"origem":"noX","destino":"noY","distancia":Z},....,{}]}
