const TestData = {

    graphvizColors: {
        a: {
            line: '#123752',
            fill: '#708EA4',
            text: '#eeeeee'
        },
        b: {
            line: '#AA8E39',
            fill: '#FFEAAA',
            text: '#806515'
        },
        c: {
            line: '#306E12',
            fill: "#519331",
            text: "#ABDD93"
        }
    },

    graphPresentation: {
        slides: [
            {
                ids: ['main', 'parse', 'main->parse'], "content": [
                {
                    "type": "Paragraph",
                    "content": [
                        {
                            "text": "hello",
                            "type": "SimpleText",
                            "content": []
                        }
                    ]
                }
            ]
            },
            {
                ids: ['listen'], "content": [
                {
                    "type": "Paragraph",
                    "content": [
                        {
                            "text": "hello world",
                            "type": "SimpleText",
                            "content": []
                        }
                    ]
                }
            ]
            }
        ]
    },

    svg: '<?xml version="1.0" encoding="UTF-8" standalone="no"?> <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN"  "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd"> <!-- Generated by graphviz version 2.38.0 (20140413.2041)  --> <!-- Title: Simple Pages: 1 --> <svg width="188pt" height="191pt"  viewBox="0.00 0.00 188.00 191.00" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"> <g id="graph0" class="graph" transform="scale(1 1) rotate(0) translate(4 187)"> <title>Simple</title> <polygon fill="white" stroke="none" points="-4,4 -4,-187 184,-187 184,4 -4,4"/> <!-- main --> <g id="node1" class="node"><title>main</title> <polygon fill="none" stroke="black" points="63,-146.5 63,-182.5 117,-182.5 117,-146.5 63,-146.5"/> <text text-anchor="middle" x="89.9966" y="-160.3" font-family="Times,serif" font-size="14.00">main [a]</text> </g> <!-- parse --> <g id="node2" class="node"><title>parse</title> <polygon fill="none" stroke="black" points="63,-73.5 63,-109.5 117,-109.5 117,-73.5 63,-73.5"/> <text text-anchor="middle" x="89.769" y="-87.3" font-family="Times,serif" font-size="14.00">parse</text> </g> <!-- main&#45;&gt;parse --> <g id="edge1" class="edge"><title>main&#45;&gt;parse</title> <path fill="none" stroke="black" d="M90,-146.455C90,-146.455 90,-119.59 90,-119.59"/> <polygon fill="black" stroke="black" points="93.5001,-119.59 90,-109.59 86.5001,-119.59 93.5001,-119.59"/> </g> <!-- listen --> <g id="node3" class="node"><title>listen</title> <polygon fill="none" stroke="black" points="0,-0.5 0,-36.5 54,-36.5 54,-0.5 0,-0.5"/> <text text-anchor="middle" x="26.6655" y="-14.3" font-family="Times,serif" font-size="14.00">listen</text> </g> <!-- parse&#45;&gt;listen --> <g id="edge2" class="edge"><title>parse&#45;&gt;listen</title> <path fill="none" stroke="black" d="M81,-73.4602C81,-50.0044 81,-12 81,-12 81,-12 64.3164,-12 64.3164,-12"/> <polygon fill="black" stroke="black" points="64.3164,-8.5001 54.3164,-12 64.3164,-15.5001 64.3164,-8.5001"/> </g> <!-- server --> <g id="node4" class="node"><title>server</title> <polygon fill="none" stroke="black" points="126,-0.5 126,-36.5 180,-36.5 180,-0.5 126,-0.5"/> <text text-anchor="middle" x="152.6" y="-14.3" font-family="Times,serif" font-size="14.00">server</text> </g> <!-- parse&#45;&gt;server --> <g id="edge3" class="edge"><title>parse&#45;&gt;server</title> <path fill="none" stroke="black" d="M99,-73.4112C99,-53.4169 99,-24 99,-24 99,-24 115.684,-24 115.684,-24"/> <polygon fill="black" stroke="black" points="115.684,-27.5001 125.684,-24 115.684,-20.5001 115.684,-27.5001"/> </g> </g> </svg> ',
    simplePage: {
        renderContext: {
            nestLevel: 1,
        },

        content: [
            {
                "title": "test",
                "type": "Section",
                "content": [
                    {
                        "componentName": "GraphVizSvg",
                        "componentProps": {
                            "diagram": {
                                "shapeSvgByStyleId": {
                                    "world": "\u003cg transform\u003d\"scale(0.4)\"\u003e\u003cg transform\u003d\"translate(-62 -62)\"\u003e\u003cpath d\u003d\"M62,0c-5,0-9.8,0.6-14.4,1.7C32.5,5.3,19.5,14.4,11,26.8C4.1,36.8,0,48.9,0,62c0,34.2,27.8,62,62,62s62-27.8,62-62   S96.2,0,62,0z M87.6,103.5c-0.5,4-6.199,6-7.399,1.7c-2.101-7.9-7.7-5.601-7.5-13c0.2-5.601-11.3-1.2-14.3-5.7   c-3-4.4,5.3-8.7,0.8-14.2S45.7,74.4,34.9,70.7c-4.8-1.7-9.4-5.601-12.6-9.5C18,56,16.5,49.8,14.7,43.4c-0.2-0.6-0.3-1.3-0.5-1.9   C19.7,28.8,30,18.7,42.9,13.6c2.4,3.3,10.1,1.7,15,2.2c2.5,0.2,4.3,1,4.4,3.2c0,0.4-0.1,1-0.3,1.6c-1.5,4.1-7.1,11.3-3.6,14   c2.6,2,4.4-1.8,5.9,3.9c0.7,2.6,1,8-1.899,9.9C59.6,50.2,52.6,43,44,47c-4.7,2.3-5.4,13.1,1.1,13.7c3.8,0.3,6.8-7.2,10.4-3.6   c2.9,2.9,5.4,10.8,12.2,11.1c6,0.3,17.5-3.4,25.1,7.1c4.5,6.4-3.8,11.5-5.3,17.601C86.2,97.9,88,100.5,87.6,103.5z\"/\u003e\u003c/g\u003e\u003c/g\u003e",
                                    "man": "\u003cg transform\u003d\"scale(0.9)\"\u003e\u003cg transform\u003d\"translate(-50 -50) scale(0.8)\"\u003e \u003cpath d\u003d\"M52.65,125.2h19.9c3.3,0,6-2.7,6-6V86.301h3.399c3.301,0,6-2.701,6-6V43.2c0-3.3-2.699-6-6-6H43.25c-3.3,0-6,2.7-6,6   v37.101c0,3.299,2.7,6,6,6h3.4V119.2C46.65,122.5,49.25,125.2,52.65,125.2z\"/\u003e \u003ccircle cx\u003d\"62.55\" cy\u003d\"15.7\" r\u003d\"15.7\"/\u003e \u003c/g\u003e\u003c/g\u003e"
                                },
                                "svg": "\u003c?xml version\u003d\"1.0\" encoding\u003d\"UTF-8\" standalone\u003d\"no\"?\u003e\n\u003c!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"\u003e\n\u003c!-- Generated by graphviz version 2.38.0 (20140413.2041)\n --\u003e\n\u003c!-- Title: Simple Pages: 1 --\u003e\n\u003csvg width\u003d\"198pt\" height\u003d\"335pt\"\n viewBox\u003d\"0.00 0.00 197.91 335.00\" xmlns\u003d\"http://www.w3.org/2000/svg\" xmlns:xlink\u003d\"http://www.w3.org/1999/xlink\"\u003e\n\u003cg id\u003d\"graph0\" class\u003d\"graph\" transform\u003d\"scale(1 1) rotate(0) translate(4 331)\"\u003e\n\u003ctitle\u003eSimple\u003c/title\u003e\n\u003cpolygon fill\u003d\"white\" stroke\u003d\"none\" points\u003d\"-4,4 -4,-331 193.908,-331 193.908,4 -4,4\"/\u003e\n\u003c!-- human --\u003e\n\u003cg id\u003d\"node1\" class\u003d\"node\"\u003e\u003ctitle\u003ehuman\u003c/title\u003e\n\u003cpolygon fill\u003d\"none\" stroke\u003d\"black\" points\u003d\"59.7234,-104.632 59.7234,-149.368 42.2578,-181 17.5576,-181 0.0920172,-149.368 0.0920172,-104.632 17.5576,-73 42.2578,-73 59.7234,-104.632\"/\u003e\n\u003ctext text-anchor\u003d\"middle\" x\u003d\"29.9077\" y\u003d\"-122.8\" font-family\u003d\"Times,serif\" font-size\u003d\"14.00\"\u003ehuman\u003c/text\u003e\n\u003c/g\u003e\n\u003c!-- world --\u003e\n\u003cg id\u003d\"node2\" class\u003d\"node\"\u003e\u003ctitle\u003eworld\u003c/title\u003e\n\u003cpolygon fill\u003d\"none\" stroke\u003d\"black\" points\u003d\"189.908,-156 131.908,-156 131.908,-98 189.908,-98 189.908,-156\"/\u003e\n\u003c/g\u003e\n\u003c!-- human\u0026#45;\u0026gt;world --\u003e\n\u003cg id\u003d\"edge6\" class\u003d\"edge\"\u003e\u003ctitle\u003ehuman\u0026#45;\u0026gt;world\u003c/title\u003e\n\u003cpath fill\u003d\"none\" stroke\u003d\"black\" d\u003d\"M59.8432,-127C80.4254,-127 101.008,-127 121.59,-127\"/\u003e\n\u003cpolygon fill\u003d\"black\" stroke\u003d\"black\" points\u003d\"121.694,-130.5 131.694,-127 121.694,-123.5 121.694,-130.5\"/\u003e\n\u003c/g\u003e\n\u003c!-- next --\u003e\n\u003cg id\u003d\"node6\" class\u003d\"node\"\u003e\u003ctitle\u003enext\u003c/title\u003e\n\u003cpolygon fill\u003d\"none\" stroke\u003d\"black\" points\u003d\"2.9077,-0.5 2.9077,-36.5 56.9077,-36.5 56.9077,-0.5 2.9077,-0.5\"/\u003e\n\u003ctext text-anchor\u003d\"middle\" x\u003d\"29.4595\" y\u003d\"-14.3\" font-family\u003d\"Times,serif\" font-size\u003d\"14.00\"\u003enext\u003c/text\u003e\n\u003c/g\u003e\n\u003c!-- human\u0026#45;\u0026gt;next --\u003e\n\u003cg id\u003d\"edge5\" class\u003d\"edge\"\u003e\u003ctitle\u003ehuman\u0026#45;\u0026gt;next\u003c/title\u003e\n\u003cpath fill\u003d\"none\" stroke\u003d\"black\" d\u003d\"M29.9077,-72.75C29.9077,-63.7407 29.9077,-54.7313 29.9077,-46.7536\"/\u003e\n\u003cpolygon fill\u003d\"black\" stroke\u003d\"black\" points\u003d\"33.4078,-46.611 29.9077,-36.6111 26.4078,-46.6111 33.4078,-46.611\"/\u003e\n\u003c/g\u003e\n\u003c!-- server --\u003e\n\u003cg id\u003d\"node3\" class\u003d\"node\"\u003e\u003ctitle\u003eserver\u003c/title\u003e\n\u003cpolygon fill\u003d\"none\" stroke\u003d\"black\" points\u003d\"67.9077,-290.5 67.9077,-326.5 121.908,-326.5 121.908,-290.5 67.9077,-290.5\"/\u003e\n\u003ctext text-anchor\u003d\"middle\" x\u003d\"94.5078\" y\u003d\"-304.3\" font-family\u003d\"Times,serif\" font-size\u003d\"14.00\"\u003eserver\u003c/text\u003e\n\u003c/g\u003e\n\u003c!-- another --\u003e\n\u003cg id\u003d\"node4\" class\u003d\"node\"\u003e\u003ctitle\u003eanother\u003c/title\u003e\n\u003cpolygon fill\u003d\"none\" stroke\u003d\"black\" points\u003d\"130.918,-217.5 130.918,-253.5 188.897,-253.5 188.897,-217.5 130.918,-217.5\"/\u003e\n\u003ctext text-anchor\u003d\"middle\" x\u003d\"159.908\" y\u003d\"-231.3\" font-family\u003d\"Times,serif\" font-size\u003d\"14.00\"\u003eanother\u003c/text\u003e\n\u003c/g\u003e\n\u003c!-- server\u0026#45;\u0026gt;another --\u003e\n\u003cg id\u003d\"edge3\" class\u003d\"edge\"\u003e\u003ctitle\u003eserver\u0026#45;\u0026gt;another\u003c/title\u003e\n\u003cpath fill\u003d\"none\" stroke\u003d\"black\" d\u003d\"M110.642,-290.313C118.706,-281.505 128.666,-270.625 137.516,-260.958\"/\u003e\n\u003cpolygon fill\u003d\"black\" stroke\u003d\"black\" points\u003d\"140.147,-263.268 144.318,-253.529 134.984,-258.541 140.147,-263.268\"/\u003e\n\u003c/g\u003e\n\u003c!-- test --\u003e\n\u003cg id\u003d\"node5\" class\u003d\"node\"\u003e\u003ctitle\u003etest\u003c/title\u003e\n\u003cpolygon fill\u003d\"none\" stroke\u003d\"black\" points\u003d\"3.9077,-217.5 3.9077,-253.5 57.9077,-253.5 57.9077,-217.5 3.9077,-217.5\"/\u003e\n\u003ctext text-anchor\u003d\"middle\" x\u003d\"30.6284\" y\u003d\"-231.3\" font-family\u003d\"Times,serif\" font-size\u003d\"14.00\"\u003etest\u003c/text\u003e\n\u003c/g\u003e\n\u003c!-- server\u0026#45;\u0026gt;test --\u003e\n\u003cg id\u003d\"edge1\" class\u003d\"edge\"\u003e\u003ctitle\u003eserver\u0026#45;\u0026gt;test\u003c/title\u003e\n\u003cpath fill\u003d\"none\" stroke\u003d\"black\" d\u003d\"M79.4151,-290.313C71.4752,-281.505 61.6683,-270.625 52.9547,-260.958\"/\u003e\n\u003cpolygon fill\u003d\"black\" stroke\u003d\"black\" points\u003d\"55.5528,-258.613 46.2576,-253.529 50.3534,-263.3 55.5528,-258.613\"/\u003e\n\u003c/g\u003e\n\u003c!-- another\u0026#45;\u0026gt;world --\u003e\n\u003cg id\u003d\"edge4\" class\u003d\"edge\"\u003e\u003ctitle\u003eanother\u0026#45;\u0026gt;world\u003c/title\u003e\n\u003cpath fill\u003d\"none\" stroke\u003d\"black\" d\u003d\"M160.068,-217.389C160.196,-203.755 160.381,-184.136 160.544,-166.75\"/\u003e\n\u003cpolygon fill\u003d\"black\" stroke\u003d\"black\" points\u003d\"164.048,-166.319 160.642,-156.287 157.048,-166.253 164.048,-166.319\"/\u003e\n\u003c/g\u003e\n\u003c!-- test\u0026#45;\u0026gt;human --\u003e\n\u003cg id\u003d\"edge2\" class\u003d\"edge\"\u003e\u003ctitle\u003etest\u0026#45;\u0026gt;human\u003c/title\u003e\n\u003cpath fill\u003d\"none\" stroke\u003d\"black\" d\u003d\"M30.747,-217.389C30.6782,-210.057 30.5931,-200.995 30.5038,-191.481\"/\u003e\n\u003cpolygon fill\u003d\"black\" stroke\u003d\"black\" points\u003d\"34.0035,-191.421 30.4096,-181.455 27.0038,-191.487 34.0035,-191.421\"/\u003e\n\u003c/g\u003e\n\u003c/g\u003e\n\u003c/svg\u003e\n",
                                "stylesByNodeId": {
                                    "human": [
                                        "man",
                                        "a"
                                    ],
                                    "world": [
                                        "world",
                                        "",
                                        "c"
                                    ],
                                    "server": [
                                        "a"
                                    ],
                                    "another": [
                                        "b"
                                    ]
                                }
                            },
                            "colors": {
                                "a": {
                                    "line": "#123752",
                                    "fill": "#708EA4",
                                    "text": "#eeeeee"
                                },
                                "b": {
                                    "line": "#AA8E39",
                                    "fill": "#FFEAAA",
                                    "text": "#806515"
                                },
                                "c": {
                                    "line": "#306E12",
                                    "fill": "#519331",
                                    "text": "#ABDD93"
                                }
                            }
                        },
                        "type": "CustomComponent"
                    },
                    {
                        "type": "Paragraph"
                    }
                ]
            }
        ],
        "docMeta": {
            "logo": "img/two-sigma-logo.png",
            "type": "User Guide",
            "title": "VATS"
        },
        "title": "External Code To Vats",
        "type": "Page",
        "toc": [
            {
                "sectionTitle": "Getting Started",
                "dirName": "getting-started",
                "items": [
                    {
                        "title": "Introduction",
                        "fileName": "introduction"
                    },
                    {
                        "title": "Setup",
                        "fileName": "setup"
                    },
                    {
                        "title": "First Code Base",
                        "fileName": "first-code-base"
                    }
                ]
            },
            {
                "sectionTitle": "External Code Bases",
                "dirName": "external-code-bases",
                "items": [
                    {
                        "title": "External Code To Vats",
                        "fileName": "external-code-to-vats"
                    },
                    {
                        "title": "Maven Import",
                        "fileName": "maven-import"
                    },
                    {
                        "title": "License Restrictions",
                        "fileName": "license-restrictions"
                    }
                ]
            },
            {
                "sectionTitle": "Cook Book",
                "dirName": "cook-book",
                "items": [
                    {
                        "title": "Merging Time Series",
                        "fileName": "merging-time-series"
                    }
                ]
            }
        ]
    }
};

export default TestData;