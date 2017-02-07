import React from 'react'

const PageSections = ({pageSectionIdTitles, selected}) => {
    return (<div className="page-sections">
        {pageSectionIdTitles.map((idTitle, idx) => {
            const isSelected = idTitle.id === selected.pageSectionId
            const className = "page-section" + (isSelected ? " active" : "")
            const href = "#" + idTitle.id

            return (<div className={className} key={idx}><a href={href}>{idTitle.title}</a></div>)
        })
        }
    </div>)
}

const Item = ({item, selected, isSelected, onClickHandler}) => {
    const className = "toc-item " + (isSelected ? "selected" : "")
    return (
        <div className={className}>
            <span onClick={ () => onClickHandler(item.dirName, item.fileName)}>{item.pageTitle}</span>
            {isSelected ? <PageSections pageSectionIdTitles={item.pageSectionIdTitles}
                                        selected={selected}/> : null}
        </div>
    );
};

const Section = ({section, selected, onClickHandler}) => {
    return (<div className="toc-section">
        <div className="title">{section.sectionTitle}</div>
        {section.items.map((item) => <Item key={item.fileName}
                                           item={item}
                                           selected={selected}
                                           isSelected={section.dirName === selected.dirName && item.fileName === selected.fileName}
                                           onClickHandler={onClickHandler} />)}
    </div>);
};

const TocMenu = ({toc, selected, onClickHandler}) => {
    selected = selected || {dirName: "", fileName: ""}

    return (
        <div className="toc-menu">
            {toc.map((sectionEntry) =>
                <Section key={sectionEntry.sectionTitle}
                    selected={selected}
                    onClickHandler={onClickHandler}
                    section={sectionEntry} />)}
        </div>
    );
};

export default TocMenu