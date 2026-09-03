import { resolverUrlImagem } from '../api/apiClient';
import CupomMini from './CupomMini.jsx';
import CountdownTimer from './CountdownTimer.jsx';

const NOME_LOJAS = {
  SHOPEE: 'Shopee',
  MERCADO_LIVRE: 'Mercado Livre',
  MAGAZINE_LUIZA: 'Magazine Luiza',
  TIKTOK_SHOP: 'TikTok Shop',
  AMAZON: 'Amazon',
  ALIEXPRESS: 'AliExpress',
  OUTRA: 'Outra loja',
};

function formatarValor(valor) {
  return new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(valor);
}

/**
 * `expiraEm` vem da seção (dia ou semana) e alimenta o cronômetro de urgência.
 * O cupom fica separado do link do produto: primeiro o usuário resgata o(s)
 * cupom(ns) aplicável(is) ao valor da oferta, depois clica em "Comprar agora".
 */
export default function OfertaCard({ oferta, expiraEm }) {
  const temPromocao = oferta.valorOriginal != null && oferta.valorOriginal > oferta.valor;
  const percentualDesconto = temPromocao
    ? Math.round((1 - oferta.valor / oferta.valorOriginal) * 100)
    : null;
  const temCupons = oferta.cupons?.length > 0;

  return (
    <div className="card">
      <a className="card__link" href={oferta.linkAfiliado} target="_blank" rel="noopener noreferrer sponsored">
        <div className="card__imagem">
          <img src={resolverUrlImagem(oferta.imagemUrl)} alt={oferta.nomeProduto} loading="lazy" />
          {temPromocao && <span className="card__badge-desconto">-{percentualDesconto}%</span>}
        </div>
        <div className="card__corpo">
          <span className="card__loja">{NOME_LOJAS[oferta.loja] ?? oferta.loja}</span>
          <h3 className="card__titulo">{oferta.nomeProduto}</h3>
          <div className="card__precos">
            {temPromocao && <span className="card__valor-original">{formatarValor(oferta.valorOriginal)}</span>}
            <span className={`card__valor ${temPromocao ? 'card__valor--promo' : ''}`}>
              {formatarValor(oferta.valor)}
            </span>
          </div>
          {expiraEm && <CountdownTimer alvo={expiraEm} />}
        </div>
      </a>

      <div className="card__rodape">
        {temCupons && (
          <div className="card__cupons">
            {oferta.cupons.map((cupom) => (
              <CupomMini key={cupom.id} cupom={cupom} />
            ))}
          </div>
        )}

        <a
          className="card__comprar"
          href={oferta.linkAfiliado}
          target="_blank"
          rel="noopener noreferrer sponsored"
        >
          {temCupons ? 'Comprar agora' : 'Ver oferta'}
        </a>
      </div>
    </div>
  );
}
