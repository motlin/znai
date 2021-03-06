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

import React, {Component} from 'react'

import {extractTextFromTokens, splitTokensIntoLines} from './codeUtils'
import LineOfTokens from './LineOfTokens.js'
import SimpleCodeToken from './SimpleCodeToken.js'
import {convertToList} from '../propsUtils'
import {isAllAtOnce} from '../meta/meta';
import {mergeWithGlobalDocReferences} from '../references/globalDocReferences'

import './tokens.css'
import './SimpleCodeSnippet.css'

class SimpleCodeSnippet extends Component {
    constructor(props) {
        super(props)

        this.processProps(props)
        this.state = {displayFully: !this.limitLines}
    }

    componentWillReceiveProps(nextProps) {
        this.processProps(nextProps)
    }

    processProps({isPresentation, tokens, linesOfCode, highlight}) {
        this.linesOfTokens = !linesOfCode ? splitTokensIntoLines(tokens) : linesOfCode

        // highlight is either a single line index/substring or a collection of line indexes and substrings
        this.highlight = convertToList(highlight)

        if (isPresentation || this.highlight.length === 0) {
            return
        }

        this.isLineHighlighted = this.linesOfTokens.map((tokens, idx) => this.isHighlighted(idx, tokens))

        if (this.isLineHighlighted.filter(highlighted => highlighted).length === 0) {
            throw new Error('none of the lines matches specified highlights: ' + this.highlight)
        }
    }

    render() {
        const {displayFully} = this.state
        const {isPresentation, slideIdx, references} = this.props

        // slideIdx === 0 means no highlights, 1 - first highlight, etc
        const highlightIsVisible = !isPresentation || slideIdx > 0

        const linesToRender = this.limitLines && !displayFully ?
            this.linesOfTokens.slice(0, this.readMoreVisibleLines) :
            this.linesOfTokens

        const mergedReferences = mergeWithGlobalDocReferences(references)

        return (
            <pre>
                {linesToRender.map((tokens, idx) => (
                    <LineOfTokens key={idx} tokens={tokens}
                                  references={mergedReferences}
                                  isHighlighted={highlightIsVisible && this.isHighlighted(idx, tokens)}
                                  isPresentation={isPresentation}
                                  TokenComponent={SimpleCodeToken}/>
                ))}
                <React.Fragment>{this.renderReadMore()}</React.Fragment>
            </pre>
        )
    }

    renderReadMore() {
        const {displayFully} = this.state

        if (displayFully) {
            return null
        }

        return (
            <div className="code-snippet-read-more" onClick={this.onReadMoreClick}>Read more...</div>
        )
    }

    onReadMoreClick = () => {
        this.setState({displayFully: true})
    }

    get limitLines() {
        return this.linesOfTokens.length >= this.readMoreVisibleLines && this.props.readMore
    }

    get readMoreVisibleLines() {
        return this.props.readMoreVisibleLines || 8
    }

    isHighlighted(idx, tokens) {
        const {meta, isPresentation, slideIdx} = this.props
        const highlight = (!isPresentation || isAllAtOnce(meta)) ?
            this.highlight:
            this.highlight.slice(0, slideIdx)

        if (! highlight) {
            return false
        }

        if (highlight.indexOf(idx) !== -1) {
            return true
        }

        const text = extractTextFromTokens(tokens)
        const matches = highlight.filter(idxOrText => {
            if (typeof idxOrText === 'number') {
                return false
            }

            return text.indexOf(idxOrText) !== -1
        })

        return matches.length > 0
    }
}

export default SimpleCodeSnippet
