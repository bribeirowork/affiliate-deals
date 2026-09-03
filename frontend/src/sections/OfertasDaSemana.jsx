import OfertaCard from '../components/OfertaCard.jsx';

export default function OfertasDaSemana({ ofertas }) {
  if (ofertas.length === 0) return null;

  return (
    <section className="secao">
      <h2 className="secao__titulo">Últimas Ofertas da Semana</h2>
      <div className="grid">
        {ofertas.map((oferta) => (
          <OfertaCard key={oferta.id} oferta={oferta} expiraEm={oferta.dataExpiracaoSemana} />
        ))}
      </div>
    </section>
  );
}
