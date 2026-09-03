import { useEffect, useMemo, useState } from 'react';
import { buscarOfertas } from './api/ofertasApi';
import Logo from './components/Logo.jsx';
import OfertaCard from './components/OfertaCard.jsx';

const ESTADO_INICIAL = {
  ofertasDoDia: [],
  ofertasDaSemana: [],
  carregando: true,
  erro: null,
};

export default function App() {
  const [estado, setEstado] = useState(ESTADO_INICIAL);
  const [busca, setBusca] = useState('');
  const [marca, setMarca] = useState('');
  const [valorMax, setValorMax] = useState('');

  useEffect(() => {
    let ativo = true;

    function carregar() {
      buscarOfertas()
        .then((ofertas) => {
          if (!ativo) return;
          setEstado({
            ofertasDoDia: ofertas.ofertasDoDia,
            ofertasDaSemana: ofertas.ofertasDaSemana,
            carregando: false,
            erro: null,
          });
        })
        .catch((erro) => {
          if (!ativo) return;
          setEstado((atual) => ({ ...atual, carregando: false, erro: erro.message }));
        });
    }

    carregar();
    const intervalo = setInterval(carregar, 30000);

    return () => {
      ativo = false;
      clearInterval(intervalo);
    };
  }, []);

  function filtrar(lista) {
    return lista.filter((o) => {
      const nomeOk = busca.trim() === '' || o.nomeProduto.toLowerCase().includes(busca.toLowerCase());
      const marcaOk = marca.trim() === '' || o.nomeProduto.toLowerCase().includes(marca.toLowerCase());
      const valorOk = valorMax === '' || o.valor <= parseFloat(valorMax);
      return nomeOk && marcaOk && valorOk;
    });
  }

  const ofertasDoDiaFiltradas = useMemo(() => filtrar(estado.ofertasDoDia), [estado.ofertasDoDia, busca, marca, valorMax]);
  const ofertasDaSemanaFiltradas = useMemo(() => filtrar(estado.ofertasDaSemana), [estado.ofertasDaSemana, busca, marca, valorMax]);

  const temFiltroAtivo = busca.trim() !== '' || marca.trim() !== '' || valorMax !== '';
  const totalResultados = ofertasDoDiaFiltradas.length + ofertasDaSemanaFiltradas.length;
  const todasOfertas = [...ofertasDoDiaFiltradas, ...ofertasDaSemanaFiltradas];

  function limparFiltros() {
    setBusca('');
    setMarca('');
    setValorMax('');
  }

  return (
    <div className="pagina">
      <header className="cabecalho">
        <Logo />
        <p className="cabecalho__descricao">
          <span className="cabecalho__destaque">Melhores promoções</span> na{' '}
          <span className="cabecalho__loja cabecalho__loja--shopee">Shopee</span>,{' '}
          <span className="cabecalho__loja cabecalho__loja--ml">Mercado Livre</span>{' '}
          e <span className="cabecalho__loja cabecalho__loja--maga">Magazine Luiza</span>.
        </p>
      </header>

      <div className="busca">
        <div className="busca__campo-texto">
          <span className="busca__icone" aria-hidden="true">🔍</span>
          <input
            className="busca__input"
            type="search"
            placeholder="Buscar produto..."
            value={busca}
            onChange={(e) => setBusca(e.target.value)}
            aria-label="Buscar produto por nome"
          />
        </div>

        <div className="busca__filtros">
          <div className="busca__campo-marca">
            <span className="busca__icone-marca" aria-hidden="true">🏷️</span>
            <input
              className="busca__input busca__input--marca"
              type="text"
              placeholder="Marca (ex: Samsung, Nike...)"
              value={marca}
              onChange={(e) => setMarca(e.target.value)}
              aria-label="Filtrar por marca"
            />
          </div>

          <div className="busca__campo-valor">
            <span className="busca__moeda">R$</span>
            <input
              className="busca__input busca__input--valor"
              type="number"
              min="0"
              step="1"
              placeholder="Preço até"
              value={valorMax}
              onChange={(e) => setValorMax(e.target.value)}
              aria-label="Preço máximo"
            />
          </div>

          {temFiltroAtivo && (
            <button className="busca__limpar" onClick={limparFiltros} aria-label="Limpar filtros">
              ✕ Limpar
            </button>
          )}
        </div>
      </div>

      <main>
        {estado.carregando && <p className="mensagem">Carregando ofertas...</p>}
        {estado.erro && <p className="mensagem mensagem--erro">Não foi possível carregar as ofertas: {estado.erro}</p>}

        {!estado.carregando && !estado.erro && (
          temFiltroAtivo ? (
            totalResultados === 0 ? (
              <p className="mensagem">Nenhuma oferta encontrada para essa busca.</p>
            ) : (
              <section className="secao">
                <p className="busca__resultado">{totalResultados} oferta{totalResultados !== 1 ? 's' : ''} encontrada{totalResultados !== 1 ? 's' : ''}</p>
                <div className="grid">
                  {todasOfertas.map((oferta) => {
                    const expiraEm = estado.ofertasDoDia.some((o) => o.id === oferta.id)
                      ? oferta.dataExpiracaoDia
                      : oferta.dataExpiracaoSemana;
                    return <OfertaCard key={oferta.id} oferta={oferta} expiraEm={expiraEm} />;
                  })}
                </div>
              </section>
            )
          ) : (
            <>
              {ofertasDoDiaFiltradas.length > 0 && (
                <section className="secao">
                  <div className="grid">
                    {ofertasDoDiaFiltradas.map((oferta) => (
                      <OfertaCard key={oferta.id} oferta={oferta} expiraEm={oferta.dataExpiracaoDia} />
                    ))}
                  </div>
                </section>
              )}
              {ofertasDaSemanaFiltradas.length > 0 && (
                <section className="secao">
                  <div className="grid">
                    {ofertasDaSemanaFiltradas.map((oferta) => (
                      <OfertaCard key={oferta.id} oferta={oferta} expiraEm={oferta.dataExpiracaoSemana} />
                    ))}
                  </div>
                </section>
              )}
            </>
          )
        )}
      </main>
    </div>
  );
}
