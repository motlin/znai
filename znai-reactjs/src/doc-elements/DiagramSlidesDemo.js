/*
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React, {Component} from "react"
import GraphVizSvg from './graphviz/GraphVizSvg'

const testData = {
    "diagram": {
        "shapeSvgByStyleId": {},
        "isInvertedTextColorByStyleId": {},
        "svg": "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"> <!-- Generated by graphviz version 2.38.0 (20140413.2041) --> <!-- Title: Generated Pages: 1 --> <svg width=\"611pt\" height=\"100pt\" viewBox=\"0.00 0.00 611.43 100.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"> <g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 96)\"> <title>Generated</title> <polygon fill=\"white\" stroke=\"none\" points=\"-4,4 -4,-96 607.429,-96 607.429,4 -4,4\"/> <!-- n1 --> <g id=\"node1\" class=\"node\"><title>n1</title> <polygon fill=\"none\" stroke=\"black\" points=\"17.2156,-55.5 17.2156,-91.5 132.271,-91.5 132.271,-55.5 17.2156,-55.5\"/> <text text-anchor=\"middle\" x=\"74.7434\" y=\"-71.8\" font-family=\"Verdana\" font-size=\"9.00\">long long text label</text> </g> <!-- n3 --> <g id=\"node3\" class=\"node\"><title>n3</title> <polygon fill=\"none\" stroke=\"black\" points=\"185.487,-27.5 185.487,-63.5 399.09,-63.5 399.09,-27.5 185.487,-27.5\"/> <text text-anchor=\"middle\" x=\"292.288\" y=\"-43.8\" font-family=\"Verdana\" font-size=\"9.00\">twosigma res snapshot nvendor dividend</text> </g> <!-- n1&#45;&gt;n3 --> <g id=\"edge1\" class=\"edge\"><title>n1&#45;&gt;n3</title> <path fill=\"none\" stroke=\"black\" d=\"M132.375,-66.1433C145.651,-64.4187 160.275,-62.519 175.113,-60.5916\"/> <polygon fill=\"black\" stroke=\"black\" points=\"175.94,-64.0137 185.405,-59.2546 175.038,-57.072 175.94,-64.0137\"/> </g> <!-- n2 --> <g id=\"node2\" class=\"node\"><title>n2</title> <polygon fill=\"none\" stroke=\"black\" points=\"0,-0.5 0,-36.5 149.487,-36.5 149.487,-0.5 0,-0.5\"/> <text text-anchor=\"middle\" x=\"74.7434\" y=\"-16.8\" font-family=\"Verdana\" font-size=\"9.00\">bloomberg instrument xref</text> </g> <!-- n2&#45;&gt;n3 --> <g id=\"edge2\" class=\"edge\"><title>n2&#45;&gt;n3</title> <path fill=\"none\" stroke=\"black\" d=\"M149.62,-27.7541C158.032,-28.8078 166.75,-29.8999 175.54,-31.0009\"/> <polygon fill=\"black\" stroke=\"black\" points=\"175.124,-34.476 185.481,-32.2462 175.994,-27.5303 175.124,-34.476\"/> </g> <!-- n4 --> <g id=\"node4\" class=\"node\"><title>n4</title> <polygon fill=\"none\" stroke=\"black\" points=\"452.147,-55.5 452.147,-91.5 586.372,-91.5 586.372,-55.5 452.147,-55.5\"/> <text text-anchor=\"middle\" x=\"519.26\" y=\"-71.8\" font-family=\"Verdana\" font-size=\"9.00\">other knowsis risk wide</text> </g> <!-- n3&#45;&gt;n4 --> <g id=\"edge3\" class=\"edge\"><title>n3&#45;&gt;n4</title> <path fill=\"none\" stroke=\"black\" d=\"M399.186,-58.6801C413.535,-60.466 428.028,-62.2697 441.725,-63.9745\"/> <polygon fill=\"black\" stroke=\"black\" points=\"441.674,-67.4951 452.03,-65.257 442.539,-60.5486 441.674,-67.4951\"/> </g> <!-- n5 --> <g id=\"node5\" class=\"node\"><title>n5</title> <polygon fill=\"none\" stroke=\"black\" points=\"435.09,-0.5 435.09,-36.5 603.429,-36.5 603.429,-0.5 435.09,-0.5\"/> <text text-anchor=\"middle\" x=\"519.26\" y=\"-16.8\" font-family=\"Verdana\" font-size=\"9.00\">yet another knowsis nrisk wide</text> </g> <!-- n3&#45;&gt;n5 --> <g id=\"edge4\" class=\"edge\"><title>n3&#45;&gt;n5</title> <path fill=\"none\" stroke=\"black\" d=\"M399.186,-32.7906C407.694,-31.7696 416.252,-30.7425 424.664,-29.7329\"/> <polygon fill=\"black\" stroke=\"black\" points=\"425.329,-33.1783 434.841,-28.5115 424.495,-26.2282 425.329,-33.1783\"/> </g> </g> </svg>",
        "stylesByNodeId": {
        },
        "id": "dag12"
    },
    "colors": {
        "a": {
            "line": "#444",
            "fill": "#fff",
            "text": "#888",
            "textInverse": "#888"
        },
        "b": {
            "line": "#AA8E39",
            "fill": "#FFEAAA",
            "text": "#806515",
            "textInverse": "#AA8E39"
        },
        "c": {
            "line": "#306E12",
            "fill": "#519331",
            "text": "#ABDD93",
            "textInverse": "#306E12"
        },
        "h": {
            "line": "#888",
            "fill": "#fff",
            "text": "#888",
            "textInverse": "#888"
        }
    },
    "idsToHighlight": ["n3"],
    "urls": {"n1": "http://google.com", "n3": "/preview/local"}
}

class DiagramSlidesDemo extends Component {
    render() {
        return <div>
            <GraphVizSvg {...testData}/></div>
    }
}

export default DiagramSlidesDemo