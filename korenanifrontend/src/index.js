import React from 'react';
import ReactDom from 'react-dom';
import 'bootstrap/dist/css/bootstrap.css'
import Counter from './component/counter';

const Element = <h1> Hello world</h1>

console.log(Element);
ReactDom.render(<Counter/>,document.getElementById('root'));
