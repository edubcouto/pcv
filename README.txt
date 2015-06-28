Identificação do Problema: Caixeiro Viajante
Problema NP. Necessário escolher uma Heurística de solução.
Devido a limitações computacionais abre-se mal da solução "perfeita" em 100% dos casos,
por uma solução viável em 100% dos casos, alguns cenários de mapas muito grandes podem
nunca chegar ao fim da execução caso não seja adotada esta estratégia.

Lembrando dos tempos de faculdade, decidi seguir a seguinte estratégia:
- A partir do nó X, identificar o Nó Y mais próximo do atual e formar o Arco XY;
- Em seguida, Identificar o nó Z mais próximo de Y, que não faça parte do caminho;
- Repetir os passos até que cheguemos ao destino, ou fiquemos "Sem Saída".
- Caso cheguemos ao destino, validar para cada Arco XY a existência de um Arco XAY 
com menor custo do que XY, repetir esse processo até não existirem mais otimizações 
possíveis.
- No caso de ficar "Sem Saída", voltaremos 1 passo atrás, escolhendo o Segundo mais
próximo, voltando sucessivamente até encontrar o destino. Feito isso, aplicaremos a
otimização de acima. 

Serão utilizada a Linguagem Java para desenvolvimento da solução, Maven para facilitar
no controle de dependências e Tomcat para ser o nosso servidor.
As escolhas se pautam pela simplicidade, logo, serão usados os seguites frameworks:
- Jersey: Para expor os Webservices;
- GSON: para simplificar a serialização e deserialização dos Objetos;
- Arquivo: Para persistir as rotas entre uma execução e outra.

JSon exemplo - Novo Mapa:
{"nomeMapa":"aqui_nome","malha":[{"origem":"noX","destino":"noY","distancia":Z},....,{}]}
