import OfertaCard from '../components/OfertaCard.jsx';

export default function OfertasDoDia({ ofertas }) {
  if (ofertas.length === 0) return null;

  return (
    <section className="secao">
      <h2 className="secao__titulo">Ofertas do Dia</h2>
      <div className="grid">
        {ofertas.map((oferta) => (
          <OfertaCard key={oferta.id} oferta={oferta} expiraEm={oferta.dataExpiracaoDia} />
        ))}
      </div>
    </section>
  );
}
