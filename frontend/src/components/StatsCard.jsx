function StatsCard({ count }) {
    return (
        <div className="stats-card">
            <div className="stats-icon">
                🚚
            </div>

            <div>
                <p>Total Vehicles</p>
                <h2>{count}</h2>
            </div>
        </div>
    );
}

export default StatsCard;