import React, { Component } from 'react'
import styleMeta from './gvStyleMeta'
import gvUtils from './gvUtils'

class GvText extends Component {
    render() {
        const {colors} = this.props
        const cleanedUpProps = gvUtils.removeCustomProps(this.props)

        return <text {...cleanedUpProps} fontFamily="verdana" fontSize="8" fill={colors.text}>
            {styleMeta.removeStyleNames(this.props.children[0])}
        </text>
    }
}

export default GvText