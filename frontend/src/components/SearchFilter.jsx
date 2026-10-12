function SearchFilter({
                          searchText,
                          setSearchText,
                          onSearch,
                          onClear
                      }) {

    return (
        <div className="filter-section">

            <div className="search-box">

                <label>Search Records</label>

                <div className="search-input-wrapper">

                    <input
                        type="text"
                        placeholder="Search by vehicle, driver, transporter, plant..."
                        value={searchText}
                        onChange={(e) =>
                            setSearchText(e.target.value)
                        }
                    />

                    <button onClick={onSearch}>
                        Search
                    </button>

                </div>

            </div>

            <button
                className="clear-button"
                onClick={onClear}
            >
                Clear
            </button>

        </div>
    );
}

export default SearchFilter;