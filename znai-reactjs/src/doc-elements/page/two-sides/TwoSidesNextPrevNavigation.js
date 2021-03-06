/*
 * Copyright 2020 znai maintainers
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

import React from 'react'
import {TwoSidesLayout, TwoSidesLayoutLeftPart, TwoSidesLayoutRightPart} from './TwoSidesLayout'
import {DefaultNextPageButton, DefaultPrevPageButton} from '../default/PageDefaultNextPrevNavigation'

import './TwoSidesNextPrevNavigation.css'

const TwoSidesNextPrevNavigation = ({currentTocItem, prevPageTocItem, nextPageTocItem, onNextPage, onPrevPage}) => {
    return (
        <TwoSidesLayout>
            <TwoSidesLayoutLeftPart>
                <div className="two-sides-prev-navigation-button">
                    <DefaultPrevPageButton currentTocItem={currentTocItem}
                                           prevTocItem={prevPageTocItem}
                                           onClick={onPrevPage}/>
                </div>
            </TwoSidesLayoutLeftPart>

            <TwoSidesLayoutRightPart>
                <div className="two-sides-next-navigation-button">
                    <DefaultNextPageButton currentTocItem={currentTocItem}
                                           nextTocItem={nextPageTocItem}
                                           onClick={onNextPage}/>
                </div>
            </TwoSidesLayoutRightPart>
        </TwoSidesLayout>
    )
}

export default TwoSidesNextPrevNavigation
