export default function SymbolHeader({symbol}:{symbol:string}) {
return (
    <header style={headerStyle}>
        <div style={symbolStyle}>{symbol}</div>
    </header>
    );
}

const headerStyle = {
    backgroundColor: '#282c34',
    height: '60px',
    display: 'flex',
    justifyContent: 'left',
    alignItems: 'center',
    color: 'white',
    boxShadow: '0 4px 2px -2px gray',
    padding: '5px' 
    };

const symbolStyle = {
    fontSize: '24px',
    fontWeight: 'bold',
};