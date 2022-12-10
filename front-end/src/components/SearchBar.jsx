import PropTypes from 'prop-types';

function SearchBar({ search, onChange }) {
    return (
        <div>
            <input
                className="input"
                type="text"
                value={search}
                onChange={onChange}
            />
        </div>
    );
}
SearchBar.propTypes = {
    search: PropTypes.any.isRequired,
    onChange: PropTypes.func.isRequired,
};

export default SearchBar;
