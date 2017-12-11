import React from 'react'
import LangClass from './LangClass';

import {elementsLibrary} from '../DefaultElementsLibrary'

const demoDef = {
    'name': 'ClassName',
    'description': 'class description',
    'methods': [
        {
            'name': 'methodOne',
            'description': 'method one description',
            'params': [
                {
                    'name': 'param1',
                    'description': 'param1 description'
                },
                {
                    'name': 'longerParam2',
                    'description': 'longer param2 description'
                }
            ]
        },
        {
            'name': 'methodTwo',
            'description': 'method two description',
            'params': [
                {
                    'name': 'param2',
                    'description': 'param2 description'
                }
            ]
        }
    ]
}

const LangClassDemo = () => {
    return (
        <div>
            <LangClass {...demoDef} elementsLibrary={elementsLibrary}/>
        </div>
    )
}

export default LangClassDemo