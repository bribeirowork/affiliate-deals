/**
 * Recriação em SVG/CSS do estilo da arte de marca (triângulo neon + estrela +
 * lettering "CUPONS Agora"), já que não há um arquivo de imagem para embutir.
 */
export default function Logo() {
  return (
    <div className="logo">
      <svg className="logo__decor" viewBox="0 0 320 220" aria-hidden="true">
        <defs>
          <filter id="neonPink" x="-60%" y="-60%" width="220%" height="220%">
            <feGaussianBlur stdDeviation="3.2" result="blur" />
            <feMerge>
              <feMergeNode in="blur" />
              <feMergeNode in="SourceGraphic" />
            </feMerge>
          </filter>
          <filter id="neonBlue" x="-80%" y="-80%" width="260%" height="260%">
            <feGaussianBlur stdDeviation="1.6" result="blur" />
            <feMerge>
              <feMergeNode in="blur" />
              <feMergeNode in="SourceGraphic" />
            </feMerge>
          </filter>
        </defs>

        <polygon
          points="45,165 205,25 285,185"
          fill="none"
          stroke="#ff2fc6"
          strokeWidth="5"
          strokeLinejoin="round"
          filter="url(#neonPink)"
        />

        <g transform="translate(24,18) scale(1.5)" filter="url(#neonBlue)">
          <path
            d="M12 2l2.9 6.6L22 9.3l-5 4.9 1.2 6.9L12 17.8l-6.2 3.3L7 14.2 2 9.3l7.1-.7L12 2z"
            fill="none"
            stroke="#4fb3ff"
            strokeWidth="1.6"
            strokeLinejoin="round"
          />
        </g>
      </svg>

      <div className="logo__texto">
        <span className="logo__cupons">OFERTA</span>
        <span className="logo__subtitulo">Hub</span>
      </div>
    </div>
  );
}
