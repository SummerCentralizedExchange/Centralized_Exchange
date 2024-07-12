import { DropdownItem } from "react-bootstrap";
import Dropdown from "react-bootstrap/esm/Dropdown";

export default function SymbolHeader({symbol, userName, symbolList}:{symbol:string, userName:string | null, symbolList:string[]}) {
    console.log(`symbolList: ${symbolList}`)

    return (
    <>
    <div style={{color:'white', textAlign:'right', padding:'5px' }}>
        <p>Welcome, {userName}!</p>
    </div>
    <header style={headerStyle}>

        <div style={symbolStyle}>
        <Dropdown>
            <Dropdown.Toggle variant="success" id="dropdown-basic" style = {{color:'white', background:'transparent', border:'none'}}>
                {symbol}
            </Dropdown.Toggle>

            <Dropdown.Menu>
                {/* Symbols */}
                {symbolList.map((item) => (<DropdownItem>item</DropdownItem>))}
            </Dropdown.Menu>
        </Dropdown>
        
        </div>
    </header>
    </>
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